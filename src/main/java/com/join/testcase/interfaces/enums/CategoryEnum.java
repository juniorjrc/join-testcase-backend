package com.join.testcase.interfaces.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoryEnum {
    CELL_PHONES("Cell phones"),
    TABLETS("Tablets"),
    COMPUTERS("Computers"),
    CONSOLES("Consoles");

    private final String categoryName;
}
