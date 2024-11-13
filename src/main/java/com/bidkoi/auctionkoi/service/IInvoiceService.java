package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.InvoiceDTO;
import com.bidkoi.auctionkoi.pojo.Invoice;

import java.util.List;


public interface IInvoiceService {

    InvoiceDTO createInvoice(Long roomId);

    InvoiceDTO getInvoice(Long roomId);

    InvoiceDTO getInvoiceByKoi(Long koiId);

    List<Invoice> getAllInvoice();

}
