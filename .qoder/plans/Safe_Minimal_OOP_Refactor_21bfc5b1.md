# Safe Minimal OOP Refactor

## Analysis Summary

- **User inheritance**: Completely missing. No Buyer/Seller/Admin subclasses exist.
- **Payment interface**: Completely missing. Pembayaran is a plain concrete class.
- **IUserDAO**: Already exists and works correctly.
- **Everything else**: Working, compilable, stable. Do not touch.

---

## Task 1: Create User Subclasses (Buyer, Seller, Admin)

Create 3 new lightweight model files. Each extends User with a copy-constructor only.

**New file**: `backend-java/src/main/java/com/greenmarket/model/Buyer.java`
```java
package com.greenmarket.model;

public class Buyer extends User {
    public Buyer() { super(); }

    public Buyer(User user) {
        super(user.getId(), user.getUsername(), user.getEmail(),
              user.getPassword(), user.getCreatedAt(), user.getRole());
        setToko(user.getToko());
    }

    public boolean isBuyer() { return true; }
}
```

**New file**: `backend-java/src/main/java/com/greenmarket/model/Seller.java`
```java
package com.greenmarket.model;

public class Seller extends User {
    public Seller() { super(); }

    public Seller(User user) {
        super(user.getId(), user.getUsername(), user.getEmail(),
              user.getPassword(), user.getCreatedAt(), user.getRole());
        setToko(user.getToko());
    }

    public String getTokoName() {
        return getToko() != null ? getToko().getNama_toko() : null;
    }
}
```

**New file**: `backend-java/src/main/java/com/greenmarket/model/Admin.java`
```java
package com.greenmarket.model;

public class Admin extends User {
    public Admin() { super(); }

    public Admin(User user) {
        super(user.getId(), user.getUsername(), user.getEmail(),
              user.getPassword(), user.getCreatedAt(), user.getRole());
    }

    public boolean canManageUsers() { return true; }
    public boolean canManageProducts() { return true; }
}
```

---

## Task 2: Add Factory Method to User

Add a static `fromUser(User)` factory method to `User.java` that returns the correct subclass based on role.

**Modify**: `backend-java/src/main/java/com/greenmarket/model/User.java`

Add at the bottom (before closing brace):
```java
public static User fromUser(User user) {
    if (user == null) return null;
    String role = user.getRole();
    if (role == null) return user;
    switch (role.toUpperCase()) {
        case "BUYER": return new Buyer(user);
        case "SELLER": return new Seller(user);
        case "ADMIN": return new Admin(user);
        default: return user;
    }
}
```

This is purely additive -- no existing code breaks.

---

## Task 3: Create Payment Interface

**New file**: `backend-java/src/main/java/com/greenmarket/model/Payment.java`
```java
package com.greenmarket.model;

import java.sql.Timestamp;

public interface Payment {
    String getId_pembayaran();
    void setId_pembayaran(String id);

    String getId_transaksi();
    void setId_transaksi(String id);

    String getStatus_pembayaran();
    void setStatus_pembayaran(String status);

    Timestamp getTanggal_pembayaran();
    void setTanggal_pembayaran(Timestamp tanggal);

    boolean isConfirmed();
    String getPaymentType();
}
```

---

## Task 4: Update Pembayaran to Implement Payment

**Modify**: `backend-java/src/main/java/com/greenmarket/model/Pembayaran.java`

- Change class declaration to `implements Payment`
- Add the two new methods required by the interface:

```java
public class Pembayaran implements Payment {
    // ... all existing code unchanged ...

    @Override
    public boolean isConfirmed() {
        return "SUCCESS".equalsIgnoreCase(status_pembayaran)
            || "PAID".equalsIgnoreCase(status_pembayaran);
    }

    @Override
    public String getPaymentType() {
        return "GENERAL";
    }
}
```

---

## Task 5: Verify Compilation

Run Maven compile to confirm everything still builds:
```
cd backend-java && mvn compile
```

---

## What is NOT Changed (Safety Guarantee)

- No changes to any DAO, Service, or Controller files
- No changes to database schema
- No changes to login/register flow
- No changes to frontend
- No changes to existing method signatures
- All existing `User` type references continue to work (subclasses ARE Users)
- All existing `Pembayaran` references continue to work (still a concrete class, now also implements Payment)

## Files Changed/Created Summary

| File | Action |
|------|--------|
| `model/Buyer.java` | CREATE (new) |
| `model/Seller.java` | CREATE (new) |
| `model/Admin.java` | CREATE (new) |
| `model/Payment.java` | CREATE (new interface) |
| `model/User.java` | MODIFY (add factory method only) |
| `model/Pembayaran.java` | MODIFY (add `implements Payment` + 2 methods) |
