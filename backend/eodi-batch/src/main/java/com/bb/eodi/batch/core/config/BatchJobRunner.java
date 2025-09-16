package com.bb.eodi.batch.core.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchJobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;
    private final EodiBatchProperties eodiBatchProperties;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String jobName = args.getOptionValues(eodiBatchProperties.jobNamePrefix()).get(0);
        Job job = jobRegistry.getJob(jobName);

        log.debug("BatchRunner run ====== jobName : {}", jobName);

        JobParametersBuilder jobParameterBuilder = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis());
        for (String optionName : args.getOptionNames()) {
            if (!optionName.startsWith(eodiBatchProperties.jobParameterPrefix())) {
                continue;
            }
            String parameterName = optionName.substring(eodiBatchProperties.jobParameterPrefix().length());
            jobParameterBuilder.addString(parameterName, args.getOptionValues(optionName).get(0));
        }

        jobLauncher.run(job, jobParameterBuilder.toJobParameters());
    }
}
