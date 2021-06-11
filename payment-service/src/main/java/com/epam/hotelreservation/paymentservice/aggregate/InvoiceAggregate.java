package com.epam.hotelreservation.paymentservice.aggregate;

import com.epam.hotelreservation.paymentservice.enums.InvoiceStatus;
import command.InvoiceCreateCommand;
import event.InvoiceCreatedEvent;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceAggregate {

  @AggregateIdentifier
  private String paymentId;
  private String uuid;
  private InvoiceStatus invoiceStatus;

  @CommandHandler
  public InvoiceAggregate(InvoiceCreateCommand invoiceCreateCommand) {
    log.info("Receive Invoice Create Command: {}", invoiceCreateCommand);
    AggregateLifecycle
        .apply(InvoiceCreatedEvent.builder()
            .paymentId(invoiceCreateCommand.getPaymentId())
            .uuid(invoiceCreateCommand.getUuid())
            .build());
  }

  @EventSourcingHandler
  protected void on(InvoiceCreatedEvent invoiceCreatedEvent) {
    this.paymentId = invoiceCreatedEvent.getPaymentId();
    this.uuid = invoiceCreatedEvent.getUuid();
    this.invoiceStatus = InvoiceStatus.PAID;
  }

}
