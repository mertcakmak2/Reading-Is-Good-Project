package com.project.readingisgood.model.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class OrderSaveRequestModel {

    @NotNull(message = "Customer Id cannot be null")
    @Min(value = 0, message = "Customer Id cannot be less than 0")
    private long customerId;
    @NotNull(message = "Book Ids cannot be null")
    @Size(min=1, message = "Book Ids length cannot be less than 0")
    private List<Long> bookIds;

    public OrderSaveRequestModel(long customerId, List<Long> bookIds) {
        this.customerId = customerId;
        this.bookIds = bookIds;
    }

    public OrderSaveRequestModel() {
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getBookIds() {
        return bookIds;
    }

    public void setBookIds(List<Long> bookIds) {
        this.bookIds = bookIds;
    }
}
