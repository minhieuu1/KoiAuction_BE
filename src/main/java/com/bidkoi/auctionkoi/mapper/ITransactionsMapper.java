package com.bidkoi.auctionkoi.mapper;

import com.bidkoi.auctionkoi.dto.TransactionsDTO;
import com.bidkoi.auctionkoi.pojo.Transactions;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ITransactionsMapper {
    Transactions toTransactions(TransactionsDTO transactionsDTO);
    TransactionsDTO toTransactionsDTO(Transactions transactions);
    List<TransactionsDTO> toTransactionsDTO(List<Transactions> transactions);
}
