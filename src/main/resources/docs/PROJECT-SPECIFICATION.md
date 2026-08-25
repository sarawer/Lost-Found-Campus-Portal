# Project Context: Lost & Found Campus Portal

I am building a professional Lost & Found Campus Portal for Southeast University (SEU), Bangladesh.

The goal is to create a real-world web application where SEU students can report lost/found items, search for items, submit claims, and safely reconnect lost items with their owners. An administrator is responsible for verifying claims and managing the platform, but the actual physical handover is handled directly by the students.

The system should be designed as a professional, production-quality application rather than a simple academic CRUD project.

---

# 1. User Roles

There are two main roles:

1. STUDENT
2. ADMIN

## STUDENT

Only Southeast University students can create accounts.

Registration must only accept university email addresses ending with:

@seu.edu.bd

Examples:

student@seu.edu.bd       -> allowed
abc@seu.edu.bd           -> allowed
student@gmail.com        -> not allowed
student@bracu.ac.bd     -> not allowed

Ideally, university email verification should also be implemented so that users cannot simply enter a fake @seu.edu.bd address.

Students can:
- Register
- Login/logout
- Manage their profile
- Create lost-item posts
- Create found-item posts
- View public lost/found posts
- Search items
- Filter items
- View item details
- Submit claims
- View their own posts
- View their submitted claims
- Receive notifications
- Receive another student's contact information after a claim is approved
- Contact the other student themselves
- Complete the physical handover themselves
- Confirm the handover through the website
- Report inappropriate/suspicious posts
- Edit/delete their own posts where appropriate

Students cannot:
- Approve claims
- Reject claims
- Access the admin dashboard
- Access other users' private information
- Edit/delete another user's posts
- Change the status of another user's item
- Approve their own claim

---

# 2. ADMIN

Admin is responsible for moderation, verification, and platform management.

Admin can:
- Login to an admin dashboard
- View all users
- Manage users
- Suspend/unsuspend users
- View all lost/found posts
- Remove inappropriate or suspicious posts
- View complete/private item information
- View all claims
- Review claims
- Approve claims
- Reject claims
- View claimant information
- View finder/owner information
- Handle user reports
- Monitor platform activity
- View statistics and dashboard metrics

Important:

ADMIN DOES NOT need to approve normal lost/found posts.

When a student creates a lost or found post, it becomes publicly visible immediately.

The admin's main responsibility is verifying claims and managing problematic content/users.

---

# 3. Lost/Found Post System

There should be one unified Item/Post concept instead of unnecessarily creating completely separate systems for Lost and Found.

An item should have a type such as:

LOST
FOUND

A student can create either type.

Example:

Rahim loses a wallet.

He creates:

Type: LOST
Item Name: Black Wallet
Description: Full private description
Lost Date/Time: 24 August, 3:30 PM
Lost Location: SEU Main Building
Image: wallet.jpg

The post becomes PUBLIC immediately.

No admin approval is required for publishing the post.

---

# 4. Public Information vs Private Information

This is a very important business rule.

Lost/found posts are public, but sensitive/detailed information must NOT be publicly exposed.

The public listing should show only limited information such as:

- Item name/title
- Item image
- LOST or FOUND type
- Poster's name/username
- Post time

The public page should NOT expose:

- Full item description
- Phone number
- Email address
- Detailed identifying information
- Private verification information
- Sensitive location information
- Claim information

Example public card:

--------------------------------
[Image]

Black Leather Wallet

LOST

Posted by: Rahim
Posted: 24 Aug 2026, 3:42 PM

[View Item]
--------------------------------

The purpose is to prevent random users from learning all identifying information and falsely claiming an item.

---

# 5. Private Item Information

The full item information must be stored in the backend/database.

Private information may include:

- Full description
- Exact/approximate lost or found date/time
- Lost/found location
- Identifying characteristics
- Additional notes
- Uploaded evidence/images
- Other verification information

This information should be accessible only to authorized users/admins according to the business rules.

The ADMIN must be able to see the full information because the admin uses it to verify claims.

---

# 6. Lost Item Flow

Example:

Rahim loses his wallet.

Flow:

Student Login
↓
Create Lost Item
↓
Enter item information
↓
Submit
↓
Post immediately becomes PUBLIC
↓
Other students can see the limited public information

Possible status:

LOST
ACTIVE

No admin approval is required at this stage.

---

# 7. Found Item Flow

Example:

Karim finds a wallet.

Flow:

Student Login
↓
Create Found Item
↓
Enter item information
↓
Submit
↓
Post immediately becomes PUBLIC
↓
Other students can search/view it

Possible status:

FOUND
ACTIVE

Again, admin approval is NOT required for publishing.

---

# 8. Search and Browse

Students should be able to browse:

- All Lost Items
- All Found Items

They should be able to search/filter by relevant fields such as:

- Item name
- Category
- Lost/Found type
- Date
- Other appropriate non-sensitive fields

Search results should only expose public information.

---

# 9. Claim System

If a student believes that a found item belongs to them, they can submit a claim.

Example:

Rahim lost a wallet.

Karim found a wallet and posted it.

Rahim sees the public listing and clicks:

[Claim Item]

The claimant should provide verification information that is NOT publicly visible.

For example:

- Why do you believe this is your item?
- Detailed description of the item
- Specific identifying characteristics
- Approximate lost date/time
- Lost location
- Additional evidence/proof if applicable

The purpose is to give the admin enough information to determine whether the claimant is the legitimate owner.

Claim status initially:

PENDING

---

# 10. Claim Verification

The ADMIN is the ONLY authority who can approve or reject a claim.

The admin compares:

1. Original lost-item information
2. Found-item information
3. Claimant's submitted information
4. Lost/found date and time
5. Item description
6. Identifying characteristics
7. Other available evidence

Example:

Lost post:

Black leather wallet
Lost around 3:30 PM
SEU Main Building
Small scratch on left corner
SEU student ID inside

Found post:

Black leather wallet
Found around 4:00 PM
Near SEU Main Building

Claim:

Black leather wallet
Small scratch on left corner
Student ID was inside
Lost around 3:30 PM

Admin compares the information privately.

The detailed information should NOT be shown publicly because that could make it easy for fake claimants to copy the details.

---

# 11. Claim Decision

Admin has two primary actions:

APPROVE
REJECT

If rejected:

Claim:
PENDING → REJECTED

The claimant receives a notification.

If approved:

Claim:
PENDING → APPROVED

The item/claim enters the handover process.

---

# 12. Important: Admin Does NOT Handle Physical Handover

The admin only verifies the claim.

After approving a claim, the actual physical handover is handled directly by the students.

The system should NOT require the admin to physically meet the students or hand over the item.

---

# 13. Contact Information After Claim Approval

Contact information must NOT be publicly visible.

Before claim approval:

Student A cannot automatically see Student B's private contact information.

After ADMIN approves the claim:

The system sends notifications to both relevant students.

The notification can include the necessary contact information of the other student.

Example notification to claimant:

"Your claim for Black Wallet has been approved by the administrator.

Found by: Karim
Contact: 01XXXXXXXXX

Please contact the finder to arrange the handover."

Example notification to finder:

"The claim for your found item Black Wallet has been approved by the administrator.

Claimed by: Rahim
Contact: 01XXXXXXXXX

Please contact the claimant to arrange the handover."

Contact information is revealed ONLY after successful claim approval.

---

# 14. Student-to-Student Handover

After receiving the contact information:

Student A and Student B communicate directly.

They decide:
- When to meet
- Where to meet
- How to hand over the item

The website does not need to manage the physical handover.

The admin is not involved unless there is a dispute/problem.

---

# 15. Handover Confirmation

After physically handing over the item, both parties should confirm through the website.

Finder sees:

[Mark as Handed Over]

Claimant sees:

[Confirm Item Received]

The system should ideally require confirmation from both sides.

Flow:

CLAIM APPROVED
↓
HANDOVER PENDING
↓
Finder confirms handover
+
Claimant confirms receipt
↓
RETURNED
↓
CLOSED

The item should no longer appear as an active lost/found item after the process is successfully completed.

---

# 16. If Only One Person Confirms

The system should not immediately mark the case as fully completed if only one party confirms.

For example:

Finder confirms:
"I handed over the item."

But claimant does not confirm.

The case can remain in a state such as:

RETURNED_BY_FINDER
or
HANDOVER_PENDING_CONFIRMATION

The admin may later review the case if necessary.

This prevents false or accidental closure.

---

# 17. Final Item Lifecycle

The item lifecycle should roughly be:

LOST / FOUND
↓
ACTIVE
↓
CLAIM PENDING
↓
CLAIM APPROVED / REJECTED

If approved:

CLAIM APPROVED
↓
HANDOVER PENDING
↓
RETURNED
↓
CLOSED

If rejected:

CLAIM REJECTED
↓
ITEM REMAINS ACTIVE

An item can potentially receive another legitimate claim if the previous claim was rejected, depending on business rules.

---

# 18. Notifications

The system should have a notification system.

Students may receive notifications for:

- Claim submitted
- Claim approved
- Claim rejected
- Contact information shared after approval
- Handover reminder
- Handover confirmation
- Item successfully closed
- Important admin actions
- Reports or account-related actions

Admins may receive notifications for:

- New claim submitted
- New user reports
- Suspicious activity
- Other important moderation events

---

# 19. Reporting System

Students should be able to report suspicious or inappropriate posts.

Possible report reasons:

- Fake information
- Spam
- Inappropriate content
- Wrong information
- Suspicious/fraudulent post
- Other

Admin can:

- View reports
- Investigate
- Remove post
- Warn user
- Suspend user
- Dismiss report

---

# 20. User Ownership and Authorization

Users must only be able to modify resources they own.

For example:

Rahim can:
- Edit Rahim's lost post
- Delete Rahim's lost post
- View Rahim's claims

Rahim cannot:
- Edit Karim's post
- Delete Karim's post
- Approve claims
- Access admin functions

Authorization must be enforced on the backend, not only through frontend/UI hiding.

---

# 21. Admin Dashboard

Admin dashboard should provide a professional overview such as:

- Total users
- Active users
- Total lost items
- Total found items
- Active items
- Pending claims
- Approved claims
- Rejected claims
- Returned items
- Closed cases
- Open reports
- Suspended users

Potential charts/statistics:

- Lost vs Found items
- Items by category
- Monthly lost/found activity
- Successful returns
- Claim success rate

---

# 22. Student Dashboard

Student dashboard should show:

- My Lost Items
- My Found Items
- Active Posts
- My Claims
- Pending Claims
- Approved Claims
- Rejected Claims
- Returned Items
- Notifications

The dashboard should make it easy for a student to track the complete lifecycle of their posts and claims.

---

# 23. Privacy and Security Rules

Important security principles:

1. Only @seu.edu.bd users can register.
2. Contact information must never be publicly displayed.
3. Detailed item descriptions must not be publicly displayed.
4. Full verification information is private.
5. Claim information is private.
6. Contact information is revealed only after admin approves the claim.
7. Students cannot approve their own or other users' claims.
8. Students cannot access admin functions.
9. Students cannot modify other users' posts.
10. Backend authorization must enforce all ownership and role restrictions.
11. Passwords must be securely hashed.
12. Sensitive data should not be unnecessarily exposed through REST/API responses.

---

# 24. Core Business Principle

The entire system should follow this principle:

"Post immediately, reveal minimally, verify privately, connect safely, and return directly."

Meaning:

- Lost/found posts are published immediately.
- Public users only see limited information.
- Detailed information remains private.
- Admin verifies claims using private information.
- Only approved claims reveal necessary contact information.
- Students arrange the physical handover themselves.
- Both parties confirm the handover through the website.
- The case is then closed.

---

# 25. Complete Example Scenario

Example:

Rahim loses a black wallet.

1. Rahim registers using rahim@seu.edu.bd.
2. Rahim logs in.
3. Rahim creates a LOST item post.
4. The post immediately becomes public.
5. Public users see:
    - Black Wallet
    - Image
    - LOST
    - Rahim
    - Post time
6. Detailed description remains private.
7. Karim finds a black wallet.
8. Karim creates a FOUND item post.
9. The post immediately becomes public.
10. Rahim sees the found wallet.
11. Rahim submits a claim.
12. Claim status becomes PENDING.
13. Admin receives the claim.
14. Admin compares the private lost information, found information, dates/times, descriptions, and identifying characteristics.
15. Admin approves the claim.
16. Claim becomes APPROVED.
17. Both Rahim and Karim receive notifications.
18. The system reveals the necessary contact information to both parties.
19. Rahim contacts Karim.
20. Karim and Rahim arrange a meeting themselves.
21. Karim physically gives the wallet to Rahim.
22. Karim confirms "Handed Over" on the website.
23. Rahim confirms "Item Received" on the website.
24. The system marks the item as RETURNED/CLOSED.
25. The item is removed from active listings but its history remains available where appropriate.

---

# 26. Technical Direction

The application should be developed as a professional Spring Boot application.

Expected architecture:

Controller
↓
DTO
↓
Service
↓
Repository
↓
Database

Use proper separation of concerns.

Avoid exposing JPA entities directly through APIs where DTOs are more appropriate.

Use:
- DTOs
- Service layer
- Repository layer
- Validation
- Global exception handling
- Proper HTTP status codes
- Authentication
- Authorization
- Role-based access control
- Secure password hashing
- Database constraints
- Pagination
- Search/filtering
- Auditing fields such as createdAt and updatedAt
- Proper logging
- Clean project structure
- Testing
- Production-ready configuration

The final application should feel like a real university platform, not a basic CRUD assignment.

When helping me with this project, always follow the business rules and user flow described above. Do not introduce conflicting behavior such as requiring admin approval for normal lost/found posts, publicly exposing full descriptions/contact information, or allowing students to approve claims.
:::