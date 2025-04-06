package com.printpoisoning.bookfull.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import com.printpoisoning.bookfull.dto.item.*;
import java.util.List;

@Getter
@Setter
@Builder
public class BookSearchResDTO {
    private int total;
    private int start;
    private int display;
    private boolean hasNext;
    private int nextStart;
    private List<BookItemDTO> items;
}
