-- MySQL schema for CC Ops Portal (monolith)

create table customer (
  id char(36) primary key,
  full_name varchar(255) not null,
  email varchar(255) not null,
  phone varchar(50) not null,
  kyc_status varchar(50) not null,
  risk_tier varchar(50) not null,
  created_at timestamp not null
);

create table account (
  id char(36) primary key,
  customer_id char(36) not null,
  product_type varchar(100) not null,
  credit_limit decimal(19,2) not null,
  available_limit decimal(19,2) not null,
  status varchar(20) not null,
  billing_day int not null,
  created_at timestamp not null
);

create table card (
  id char(36) primary key,
  account_id char(36) not null,
  card_token varchar(80) not null unique,
  last4 varchar(4) not null,
  status varchar(20) not null,
  emboss_name varchar(255) not null,
  expiry_month int not null,
  expiry_year int not null
);

create table card_block (
  id char(36) primary key,
  card_id char(36) not null,
  reason varchar(50) not null,
  note varchar(500) not null,
  requested_by varchar(100) not null,
  decided_by varchar(100),
  status varchar(50) not null,
  created_at timestamp not null,
  decided_at timestamp null
);

create table approval_request (
  id char(36) primary key,
  policy_key varchar(50) not null,
  payload_ref char(36) not null,
  status varchar(20) not null,
  requested_by varchar(100) not null,
  decided_by varchar(100),
  decision_note varchar(500),
  created_at timestamp not null,
  decided_at timestamp null
);

create table txn (
  id char(36) primary key,
  account_id char(36) not null,
  card_id char(36) not null,
  amount decimal(19,2) not null,
  currency varchar(10) not null,
  mcc varchar(10) not null,
  merchant_name varchar(255) not null,
  txn_time timestamp not null,
  status varchar(20) not null,
  auth_code varchar(20) not null
);

create table dispute (
  id char(36) primary key,
  customer_id char(36) not null,
  account_id char(36) not null,
  txn_id char(36) not null,
  reason_code varchar(50) not null,
  status varchar(30) not null,
  amount decimal(19,2) not null,
  created_at timestamp not null
);

create table audit_event (
  id char(36) primary key,
  `timestamp` timestamp not null,
  actor varchar(100) not null,
  action varchar(60) not null,
  entity_type varchar(60) not null,
  entity_id varchar(60) not null,
  correlation_id varchar(60) not null,
  outcome varchar(30) not null,
  details_json varchar(2000)
);

-- Seed demo data
insert into customer(id, full_name, email, phone, kyc_status, risk_tier, created_at) values
  ('c1111111-1111-1111-1111-111111111111','Asha Sharma','asha@example.com','9999912345','VERIFIED','LOW', now()),
  ('c2222222-2222-2222-2222-222222222222','Rahul Mehta','rahul@example.com','8888812345','VERIFIED','MEDIUM', now());

insert into account(id, customer_id, product_type, credit_limit, available_limit, status, billing_day, created_at) values
  ('a1111111-1111-1111-1111-111111111111','c1111111-1111-1111-1111-111111111111','PLATINUM',200000,150000,'ACTIVE',10,now()),
  ('a2222222-2222-2222-2222-222222222222','c2222222-2222-2222-2222-222222222222','GOLD',100000,90000,'ACTIVE',5,now());

insert into card(id, account_id, card_token, last4, status, emboss_name, expiry_month, expiry_year) values
  ('k1111111-1111-1111-1111-111111111111','a1111111-1111-1111-1111-111111111111','tok_asha_01','1234','ACTIVE','ASHA SHARMA',12,2028),
  ('k2222222-2222-2222-2222-222222222222','a2222222-2222-2222-2222-222222222222','tok_rahul_01','5678','ACTIVE','RAHUL MEHTA',11,2027);

insert into txn(id, account_id, card_id, amount, currency, mcc, merchant_name, txn_time, status, auth_code) values
  ('t1111111-1111-1111-1111-111111111111','a1111111-1111-1111-1111-111111111111','k1111111-1111-1111-1111-111111111111',1299.00,'INR','5411','Big Bazaar', now() - interval 2 day, 'CAPTURED','A1B2C3'),
  ('t2222222-2222-2222-2222-222222222222','a2222222-2222-2222-2222-222222222222','k2222222-2222-2222-2222-222222222222',4999.00,'INR','5732','Croma', now() - interval 5 day, 'CAPTURED','Z9Y8X7');
