package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.InvoiceDTO;
import com.bidkoi.auctionkoi.pojo.Invoice;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IInvoiceMapper {
    InvoiceDTO toInvoiceDTO(Invoice invoice);
}
