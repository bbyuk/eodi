package com.bb.eodi.address.job.tasklet;

import com.bb.eodi.address.domain.service.AddressLinkageApiCallService;
import com.bb.eodi.address.domain.service.AddressLinkagePeriod;
import com.bb.eodi.address.domain.service.AddressLinkageResult;
import com.bb.eodi.address.domain.service.AddressLinkageTarget;
import lombok.RequiredArgsConstructor;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;

@RequiredArgsConstructor
public class AddressLinkageApiCallTasklet implements Tasklet {


    private final AddressLinkageTarget target;
    private final AddressLinkageApiCallService addressLinkageApiCallService;
    private final String targetDirectory;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        // targetDirectory 없으면 mkdir
        File targetDirectoryObj = new File(targetDirectory);
        if (!targetDirectoryObj.exists()) {
            targetDirectoryObj.mkdirs();
        }

        if (!targetDirectoryObj.isDirectory()) {
            throw new RuntimeException("Target directory is not a directory: " + targetDirectory);
        }

        ExecutionContext jobCtx = contribution.getStepExecution().getJobExecution().getExecutionContext();

        AddressLinkagePeriod targetPeriod = addressLinkageApiCallService.findTargetPeriod(target.getReferenceVersionName());
        AddressLinkageResult addressLinkageResult;

        if (target == AddressLinkageTarget.ROAD_NAME_ADDRESS_KOR) {
            addressLinkageResult = addressLinkageApiCallService.downloadNewFiles(targetDirectory, targetPeriod);
        }
        else if (target == AddressLinkageTarget.ADDRESS_ENTRANCE) {
            addressLinkageResult = addressLinkageApiCallService.downloadEntranceLinkageFiles(targetDirectory, targetPeriod);
        }
        else {
            throw new RuntimeException("연동처리 되지 않은 주소 연계 API 요청입니다.");
        }

        if (AddressLinkageResult.ALREADY_UP_TO_DATE == addressLinkageResult) {
            contribution.setExitStatus(new ExitStatus("SKIP"));
            return RepeatStatus.FINISHED;
        }

        jobCtx.put("fromDate", targetPeriod.from());
        jobCtx.put("toDate", targetPeriod.to());

        jobCtx.put("targetDate", targetPeriod.from());

        return RepeatStatus.FINISHED;
    }
}
