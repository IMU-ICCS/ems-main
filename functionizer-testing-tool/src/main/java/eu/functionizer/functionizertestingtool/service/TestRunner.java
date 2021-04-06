package eu.functionizer.functionizertestingtool.service;

import eu.passage.upperware.commons.model.testing.FunctionizerTestResult;
import eu.functionizer.functionizertestingtool.service.test.FunctionizerReportData;
import eu.functionizer.functionizertestingtool.service.test.FunctionizerTestListener;
import eu.functionizer.functionizertestingtool.service.test.ServerlessFunctionTestFactory;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;


@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TestRunner {

    public FunctionizerTestResult runTests() {

        log.debug("Initiating Test Launcher");
        Launcher launcher = LauncherFactory.create();

        log.debug("Collecting test classes");
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
            .selectors(selectClass(ServerlessFunctionTestFactory.class))
            .build();

        TestPlan testPlan = launcher.discover(request);

        log.debug("Initiating Listener");

        FunctionizerTestListener listener = new FunctionizerTestListener();

        log.debug("Registering Listener");
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        log.info("Generating Report");
        FunctionizerReportData report = listener.getReport();
        log.info("Test run ended");
        return report.getTestResult();
    }
}
