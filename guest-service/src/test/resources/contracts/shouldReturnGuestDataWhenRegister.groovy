package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return guest data when create new guest"
    request {
        method POST()
        url("/guests")
        headers {
            contentType applicationJson()
        }
        body(
                "username": "dat1234",
                "password": '$2a\$10$WnH7gAPJAr89XvgK7zwO6upwVz1jOZGTvbagVt2ZVm/hiocEnc/Mm',
                "salt": '$2a$10$WnH7gAPJAr89XvgK7zwO6u',
                "guestName": "Dat Nguyen",
                "address": "53 Vietnam",
                "phoneNumber": "0123456789"
        )
    }
    response {
        headers {
            contentType applicationJson()
        }
        body(
                "username": "dat1234",
                "password": $(anyNonEmptyString()),
                "salt": $(anyNonEmptyString()),
                "guestName": "Dat Nguyen",
                "address": "53 Vietnam",
                "phoneNumber": "0123456789"
        )
        status 200
    }
}
