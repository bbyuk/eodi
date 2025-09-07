package com.bb.eodi.batch.legaldong.load.writer;

import com.bb.eodi.domain.legaldong.entity.LegalDong;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

/**
 * 법정동 코드 적재 배치 writer
 */
@Component
@RequiredArgsConstructor
public class LegalDongRowWriter implements ItemWriter<LegalDong> {
}
