package com.ijiagoushi.chillies.easypoi.excel;

import java.util.List;

/**
 * <p>
 * 内部的ExcelHeader
 * </p>
 *
 * @author mzlion on 2016/6/15.
 */
class InternalExcelHeader {

    String title;

    int cellIndex;

    int rowIndex;

    int startRow = -1;

    int endRow = -1;

    int startCol = -1;

    int endCol = -1;

    List<InternalExcelHeader> childExcelHeaders;

    boolean inCol(int col) {
        return col >= this.startCol && col <= this.endCol;
    }

    boolean inRow(int row) {
        return row >= this.startRow && row <= this.endRow;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InternalExcelHeader{");
        sb.append("title='").append(title).append('\'');
        sb.append(", cellIndex=").append(cellIndex);
        sb.append(", startRow=").append(startRow);
        sb.append(", endRow=").append(endRow);
        sb.append(", startCol=").append(startCol);
        sb.append(", endCol=").append(endCol);
        sb.append(", childExcelHeaders=").append(childExcelHeaders);
        sb.append('}');
        return sb.toString();
    }
}
