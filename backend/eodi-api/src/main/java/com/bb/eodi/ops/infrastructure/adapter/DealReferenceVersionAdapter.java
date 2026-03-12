package com.bb.eodi.ops.infrastructure.adapter;

import com.bb.eodi.deal.application.port.DealReferenceVersionPort;
import com.bb.eodi.ops.domain.repository.ReferenceVersionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DealReferenceVersionAdapter implements DealReferenceVersionPort {

    private final ReferenceVersionRepository referenceVersionRepository;

    @Override
    public LocalDate findLastUpdatedDate(List<String> targetNames) {
        return referenceVersionRepository.findLastUpdateDate(targetNames)
                .orElseThrow(() -> new RuntimeException("Could not find last updated date at: " + targetNames.toString()));
    }
}
