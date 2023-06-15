package com.kist.portal.common.chems.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 그리드에서 가져온 row 정보의 트랜잭션 (CREATE/UPDATE/DELETE) 를 표기하는 코드값.
 * @author miri
 * @version 2023-06-01
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum RowStatusType {
    N("NONE"),
    C("CREATE"),
    U("UPDATE"),
    D("DELETE");

    // 개발자 내부용 디스크립션
    private String description;
}
