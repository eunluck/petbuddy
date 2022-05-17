package com.petbuddy.api.configure.support;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

public class SimpleOffsetPageRequest implements Pageable {

  private final long offset;

  private final int limit;

  public SimpleOffsetPageRequest() {
    this(0, 5);
  }

  public SimpleOffsetPageRequest(long offset, int limit) {
    checkArgument(offset >= 0, "Offset must be greater or equals to zero.");
    checkArgument(limit >= 1, "Limit must be greater than zero.");

    this.offset = offset;
    this.limit = limit;
  }

  @Override
  public long offset() {
    return offset;
  }

  @Override
  public int limit() {
    return limit;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("offset", offset)
      .append("limit", limit)
      .toString();
  }

}