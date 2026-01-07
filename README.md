# Credit Card Ops Portal (Monolith) - Java 17 + Spring Boot

## What you get
A runnable end-to-end monolithic backend implementing:
- Customer search + customer details
- Accounts list by customer
- Cards list by account + card details
- Block/unblock card with maker-checker approvals
- Transaction search
- Dispute create + list + get
- Full audit trail
- Basic Auth + RBAC (in-memory users)
- MySQL (Flyway schema + seed data)
- Swagger UI

## Run

1) Start MySQL
```bash
docker compose up -d
```

2) Run the app
```bash
mvn spring-boot:run
```

App: http://localhost:8080  
Swagger UI: http://localhost:8080/swagger-ui/index.html

## Demo users (Basic Auth)
- agent / agent123  (ROLE_OPS_AGENT)
- supervisor / super123 (ROLE_OPS_SUPERVISOR)
- auditor / audit123 (ROLE_AUDITOR)
- admin / admin123 (ROLE_ADMIN)

## Quick curl
```bash
curl -u agent:agent123 "http://localhost:8080/api/customers?query=asha"
curl -u agent:agent123 "http://localhost:8080/api/customers/c1111111-1111-1111-1111-111111111111/accounts"
curl -u agent:agent123 "http://localhost:8080/api/accounts/a1111111-1111-1111-1111-111111111111/cards"
```

### Request block (creates approval)
```bash
curl -u agent:agent123 -H "Content-Type: application/json" \
  -d '{"reason":"LOST","note":"customer called"}' \
  "http://localhost:8080/api/cards/k1111111-1111-1111-1111-111111111111/block"
```

### Supervisor approves (executes block)
```bash
curl -u supervisor:super123 "http://localhost:8080/api/approvals"
curl -u supervisor:super123 -H "Content-Type: application/json" \
  -d '{"note":"Approved - lost card"}' \
  "http://localhost:8080/api/approvals/{approvalId}/approve"
```

### Create dispute
```bash
curl -u agent:agent123 -H "Content-Type: application/json" \
  -d '{"customerId":"c1111111-1111-1111-1111-111111111111","accountId":"a1111111-1111-1111-1111-111111111111","txnId":"t1111111-1111-1111-1111-111111111111","reasonCode":"FRAUD","amount":1299.00}' \
  "http://localhost:8080/api/disputes"
```

### Auditor reads audit events for a card
```bash
curl -u auditor:audit123 "http://localhost:8080/api/audit?entityType=Card&entityId=k1111111-1111-1111-1111-111111111111"
```

## Production swaps
- Replace Basic Auth with OIDC (Keycloak) and H2 with Postgres.
- Use tokenized PAN only (cardToken) + last4; never store raw PAN.
