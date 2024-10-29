package com.bidkoi.auctionkoi.service;

import com.bidkoi.auctionkoi.dto.InvoiceDTO;

public interface IInvoiceService {

    InvoiceDTO createInvoice(Long roomId);

    InvoiceDTO getInvoice(Long roomId);
}
