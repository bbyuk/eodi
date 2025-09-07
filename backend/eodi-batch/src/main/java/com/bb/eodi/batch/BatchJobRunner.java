package com.bb.eodi.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
@Component
@RequiredArgsConstructor
public class BatchJobRunner implements ApplicationRunner {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;
    private static final String PARAMETER_PREFIX = "job.parameters.";
    private static final String JOBNAME_PREFIX = "job.name";

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String jobName = args.getOptionValues(JOBNAME_PREFIX).get(0);
        Job job = jobRegistry.getJob(jobName);

        JobParametersBuilder jobParameterBuilder = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis());
        for (String optionName : args.getOptionNames()) {
            if (!optionName.startsWith(PARAMETER_PREFIX)) {
                continue;
            }
            String parameterName = optionName.substring(PARAMETER_PREFIX.length());
            jobParameterBuilder.addString(parameterName, args.getOptionValues(optionName).get(0));
        }

        jobLauncher.run(job, jobParameterBuilder.toJobParameters());
    }
}
