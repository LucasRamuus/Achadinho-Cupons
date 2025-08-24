CREATE TABLE product (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    old_price DECIMAL(10, 2),
    description TEXT,
    discount_percentage DECIMAL(5, 2),
    image TEXT,
    affiliate_link VARCHAR(500)
);