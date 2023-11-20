## Users

| Property        | Type                                | Default       | Nullable | Relationship                | Remarks                                            |
| --------------- | ----------------------------------- | ------------- | -------- | --------------------------- | -------------------------------------------------- |
| UserID          | primary key,<br />integer, identity |               | No       | Relate to<br />Sales.UserID | Key:<br />Initial Value = 1<br />Increment = 1     |
| Username        | varchar(30)                         |               | No       |                             | String with max<br />length of 30 characters       |
| Password        | varchar(30)                         |               | No       |                             | 6 characters minimum<br />is necessary             |
| ShippingAddress | varchar(100)                        | Empty<br />"" | Yes      |                             | String with max<br />length of 100 characters      |
| isAdmin         | boolean                             | False         | No       |                             | True or False <br />values for user's admin status |

## Inventory

| Property    | Type                                | Default | Nullable | Relationship                     | Remarks                                                      |
| ----------- | ----------------------------------- | ------- | -------- | -------------------------------- | ------------------------------------------------------------ |
| InventoryID | primary key,<br />integer, identity |         | No       | Relate to<br />Sales.InventoryID | Key:<br />Initial Value = 1<br />Increment = 1               |
| Name        | varchar(80)                         |         | No       |                                  | String with max<br />length of 80 characters                 |
| Description | varchar(300)                        |         | No       |                                  | String with max<br />length of 300 characters                |
| Price       | numeric(8,2)                        |         | No       |                                  | Numeric value with<br />max total of 8 digits; <br />has 2 decimal points |




## Shipping Type

| Property       | Type                                | Default | Nullable | Relationship                         | Remarks                                                      |
| -------------- | ----------------------------------- | ------- | -------- | ------------------------------------ | ------------------------------------------------------------ |
| ShippingTypeID | primary key,<br />integer, identity |         | No       | Relate to <br />Sales.ShippingTypeID | Key:<br />Initial Value = 1<br />Increment = 1               |
| Name           | varchar(60)                         |         | No       |                                      | String with max<br />length of 60 characters                 |
| Price          | numeric(8,2)                        |         | No       |                                      | Numeric value with<br />max total of 8 digits; <br />has 2 decimal points |



## Sales

| Property    | Type                                | Default | Nullable | Relationship                         | Remarks                                                      |
| ----------- | ----------------------------------- | ------- | -------- | ------------------------------------ | ------------------------------------------------------------ |
| UserID      | primary key,<br />integer, identity |         | No       | Relate to <br />Users.UserID         |                                                              |
| InventoryID | primary key,<br />integer, identity |         | No       | Relate to<br />Inventory.InventoryID |                                                              |
|ShippingTypeID | primary key,<br />integer, identity |       | No      | Relate to <br />Shipping.ShippingTypeID |             |
| DateOfSale  | date                                |         | No       |                                      | Format: YYYY-MM<br />-DD.                                    |
| Tax (6%)     |numeric(8,2)                         |         | No       |                                      | Numeric value with<br />max total of 8 digits; has 2 decimal points                                     |
| GrandTotal  | numeric(8,2)                        |         | No       |                                      | Numeric value with<br />max total of 8 digits; <br />has 2 decimal points |


