package asthux.EBFF.response;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;
@Getter
public class EbffPage<T> {
  private List<T> contents;

  private int pageNumber;
  private int pageSize;
  private int totalPages;
  private long totalCount;

  public static <T> EbffPage<T> of(Page<T> pagedContents) {
    EbffPage<T> converted = new EbffPage<>();
    converted.contents = pagedContents.getContent();
    converted.pageNumber = pagedContents.getNumber();
    converted.pageSize = pagedContents.getSize();
    converted.totalPages = pagedContents.getTotalPages();
    converted.totalCount = pagedContents.getTotalElements();
    return converted;
  }
}
