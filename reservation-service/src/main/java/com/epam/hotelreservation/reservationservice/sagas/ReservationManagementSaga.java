package com.epam.hotelreservation.reservationservice.sagas;

import command.InvoiceCreateCommand;
import event.InvoiceCreatedEvent;
import event.ReservationCreatedEvent;
import java.util.UUID;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

@Saga
@Slf4j
public class ReservationManagementSaga {


  @Inject
  private transient CommandGateway commandGateway;


  @StartSaga
  @SagaEventHandler(associationProperty = "uuid")
  public void handle(ReservationCreatedEvent reservationCreatedEvent) {
    var paymentId = UUID.randomUUID().toString();
    log.info("Saga invoked");
    SagaLifecycle.associateWith("paymentId", paymentId);
    log.info("Reservation id: {}", reservationCreatedEvent.getUuid());
    commandGateway.send(InvoiceCreateCommand.builder()
        .paymentId(paymentId)
        .uuid(reservationCreatedEvent.getUuid())
        .build()
    );
  }

  @SagaEventHandler(associationProperty = "paymentId")
  public void handle(InvoiceCreatedEvent invoiceCreatedEvent) {
    log.info("Receive invoice created event: {}", invoiceCreatedEvent);
    SagaLifecycle.end();
    log.info("Saga ended");
  }

}
