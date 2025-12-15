![Entity-relationship model](ERD.png)

## Admin Capabilities

### Product

- Create product
- Update product details
- Activate / Deactivate product
- View all products (including inactive)

### Order:

- Change order status (NEW → CONFIRMED → COMPLETED / CANCELED)
- Update orders only while status is NEW
- View all orders with pagination and filtering by status

### Category:

- Create category
- Update Category
- Activate/ Deactivate

### Accounts:

- View all users with pagination and filtering by status
- View by id
- Block or activate (Handle LockedException and DisabledException)
- Give role

-----------------------------------------------------------------------
## User Capabilities

### Products:

- View all active products with pagination and filtering by status
- Search products by name

### Order:

- Create Order
- Update or cancel orders only while status is NEW
- View only his orders

### Category:

- View all active categories
- View categories associated with available products

### Accounts

- Inactivate own account
- Update own account
- Change password
