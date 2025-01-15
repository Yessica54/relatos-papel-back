package com.unir.grupo_12.ms_books_payments.services;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.unir.grupo_12.ms_books_payments.client.BookConsumer;
import com.unir.grupo_12.ms_books_payments.dtos.BookDTO;
import com.unir.grupo_12.ms_books_payments.dtos.RequestPaymentDto;
import com.unir.grupo_12.ms_books_payments.dtos.ResponseDTO;
import com.unir.grupo_12.ms_books_payments.model.Payment;
import com.unir.grupo_12.ms_books_payments.repository.PaymentRepository;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BookConsumer bookConsumer;
    @Autowired
    private ModelMapper modelMapper;

   
    public ResponseDTO save(RequestPaymentDto requestPaymentDto){
        for (BookDTO book : requestPaymentDto.getBooks()) {
            try {
                ResponseEntity<BookDTO> bookInfo = bookConsumer.getBook(book.getId());
                BookDTO bookResponse =  bookInfo.getBody();
                if(bookResponse == null)
                    return new ResponseDTO(HttpStatus.NOT_FOUND, "Ha ocurrido un error al obtener información del libro id: "+book.getId());
                bookResponse.setQuantity(book.getQuantity());
                if(!bookResponse.isValid())
                    return new ResponseDTO(HttpStatus.BAD_REQUEST, "Es invalido el status del libro ID:"+book.getId());
            } catch (HttpClientErrorException e) {
                logger.error("[BOOK ID: {}]Error while fetching book info for book {}",book.getId() ,e.getMessage(), e);
                return new ResponseDTO(HttpStatus.NOT_FOUND, "Error obteniendo información libro ID:"+book.getId());
            }catch (Exception e) {
                logger.error("Unexpected error: {}", e.getMessage(), e);
                return new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Ha ocurrido un error");
            }
        }
        Payment payment = modelMapper.map(requestPaymentDto, Payment.class);
        paymentRepository.save(payment);
        return  new ResponseDTO(HttpStatus.OK, "Se ha guardado la compra con exito");
    }
}