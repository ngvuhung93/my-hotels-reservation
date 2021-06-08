package contracts

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "Should return guest data when get login info"
    request {
        method GET()
        urlPath('/guests') {
            queryParameters {
                parameter username: equalTo("dat1234")
            }
        }
    }
    response {
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
        status 200
    }
}
