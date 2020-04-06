package at.home;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.BatchV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Job;
import io.kubernetes.client.openapi.models.V1JobBuilder;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.proto.V1Batch.Job;
import io.kubernetes.client.util.ClientBuilder;

@RestController
public class K8sRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(K8sRestController.class);

    private CoreV1Api coreApi;
    private BatchV1Api batchApi;

    @PostConstruct
    void init() throws IOException {
        Configuration.setDefaultApiClient(ClientBuilder.cluster().build());
        coreApi = new CoreV1Api();
        batchApi = new BatchV1Api();
    }

    @RequestMapping(value = "/k8s/pods", produces = {"application/json"}, method = RequestMethod.GET)
    public ResponseEntity<List<String>> getPods() throws ApiException {

        V1PodList pods =
            coreApi.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);
        return ResponseEntity.ok(pods.getItems().stream().map(i -> Objects.requireNonNull(i.getMetadata()).getName()).collect(Collectors.toList()));
    }

    @PostMapping(value = "/k8s/createJob/{exportId}")
    public ResponseEntity<String> createJob(@PathVariable Long exportId) throws ApiException {
        LOGGER.info("Creating Job with exportId = {}", exportId);

        V1Job exportJob = new V1JobBuilder().withApiVersion("batch/v1").withNewMetadata().withGenerateName("export-job-").endMetadata()
            .withNewSpec()
            .withNewTemplate().withNewMetadata()
            .withName("export-job").endMetadata().withNewSpec().addNewContainer().withName("export-job").withImage("me/export-job:v1.0")
            .withArgs("java", "-jar", "export-job.jar", String.valueOf(exportId))
            .endContainer().withRestartPolicy("OnFailure").endSpec().endTemplate().endSpec().build();

        batchApi.createNamespacedJob("default", exportJob, null, null, null);
        // Delete all completed jobs
        // kubectl delete job $(kubectl get job -o=jsonpath='{.items[?(@.status.succeeded==1)].metadata.name}')
        return ResponseEntity.ok("Job for exportId " + exportId + " created nice and smooth.");
    }
}
