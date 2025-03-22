# 🛒 E-commerce Website for Electronic Devices

![image](https://github.com/user-attachments/assets/d412156b-c58f-46bc-861a-36c560d1b302)
![image](https://github.com/user-attachments/assets/b1d6ee72-f77e-4cef-8614-5dfb1c12ccf1)
![image](https://github.com/user-attachments/assets/f5a1be08-9836-43cc-b049-0240d120fcd2)
![image](https://github.com/user-attachments/assets/b22e7d39-22b8-479d-b12d-3222bd40c1eb)
![image](https://github.com/user-attachments/assets/03ddcd6f-7e46-4f19-8243-0a328636af16)

## 🚀 Introduction
This e-commerce website for electronic devices is developed using **Spring Boot (MVC), MySQL, Bootstrap, Spring Security, VNPay, and Thymeleaf**. 
The system supports a full range of online shopping, payment, and product management features.

## 🌟 Technologies Used
- **Backend:** Spring Boot, Spring Data JPA, Spring Security (JWT)
- **Frontend:** Thymeleaf, Bootstrap
- **Database:** MySQL
- **Payment Gateway:** VNPay
- **User Roles:** User, Admin

## 🎯 Key Features
### 🔹 Customer Features
- 🛒 **Shopping Cart**: Add/remove products, update quantity
- 💳 **VNPay Payment**: Supports online payment
- ⭐ **Ratings & Reviews**: Users can rate and review products
- 🎁 **Promotions & Vouchers**: Apply discounts if available

### 🔹 Admin Features
- 📦 **Manage Products**: Add, edit, delete electronic devices
- 📂 **Manage Product Categories**
- 🔖 **Manage Vouchers**: Create & manage discount codes
- 🎉 **Manage Promotions**: Enable/disable product promotions
- 🔐 **User Role Management**: Assign User and Admin roles

## 🛠️ Installation & Setup
### 📌 System Requirements
- **JDK 17+**
- **MySQL 8.0+**
- **Maven 3+**

### 🔧 Database Configuration
Update the `application.properties` file with your MySQL details:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

### ▶️ Run the Application
1. **Clone the repository**:
   ```sh
   git clone https://github.com/your-repo/ecommerce-springboot.git
   cd ecommerce-springboot
   ```
2. **Install dependencies**:
   ```sh
   mvn clean install
   ```
3. **Run the application**:
   ```sh
   mvn spring-boot:run
   ```

### 🌍 Access the System
- **Homepage:** `http://localhost:8080`
- **Admin Panel:** `http://localhost:8080/admin`

## 🔗 VNPay Integration
1. Register for a VNPay account and obtain the API key.
2. Configure it in `application.properties`:
   ```properties
   vnpay.tmnCode=your_tmn_code
   vnpay.hashSecret=your_hash_secret
   ```
3. During checkout, the system redirects users to VNPay for payment processing.

## 📜 License
This project is developed for educational and research purposes. Please give credit if used.


