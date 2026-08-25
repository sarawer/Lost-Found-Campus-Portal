# SEU CampusFinder — UI/UX Design Specification

Design and build a modern, professional, responsive web UI for a university Lost & Found platform called:

SEU CampusFinder

University:
Southeast University (SEU), Bangladesh

Tagline:
"Find it. Verify it. Get it back."

The application is designed specifically for SEU students and administrators.

The UI should feel like a modern SaaS/product platform rather than a basic university CRUD project.

==================================================
1. DESIGN GOAL
   ==================================================

Create a clean, modern, trustworthy, student-friendly interface.

The design should communicate:

- Trust
- Simplicity
- Security
- Community
- Fast discovery
- Professionalism

Avoid:
- Overly complicated layouts
- Excessive gradients
- Too many colors
- Old-fashioned Bootstrap-looking interfaces
- Excessive animations
- Crowded dashboards
- Generic "admin template" appearance

The UI should feel similar in quality to a modern startup/web application.

Use:
- Clean spacing
- Rounded cards
- Subtle shadows/borders
- Clear typography hierarchy
- Consistent buttons
- Modern icons
- Responsive layouts
- Accessible contrast
- Clear status badges
- Smooth but subtle hover effects

==================================================
2. BRANDING
   ==================================================

Primary brand:

SEU CampusFinder

Use a simple modern visual identity.

Suggested logo concept:

A combination of:
- Location/search/finder symbol
- Small lost-and-found/item concept
- SEU initials

Do not make the logo look like a generic Google Maps/location app.

The brand should communicate:
"Finding and returning lost items on campus."

Suggested tagline:

Find it. Verify it. Get it back.

Use a professional color palette.

Suggested direction:

Primary:
Deep blue / navy

Secondary:
Blue / indigo

Success:
Green

Warning:
Amber

Danger:
Red

Neutral:
White / light gray / dark gray

Do not use too many accent colors.

==================================================
3. GLOBAL LAYOUT
   ==================================================

Desktop:

------------------------------------------------
Logo        Home  Lost Items  Found Items
How It Works       Search
Notifications
Profile
------------------------------------------------

Main content

Footer
------------------------------------------------

Mobile:

Top:
Logo + menu button

Navigation should collapse into a mobile-friendly menu.

The entire application must be fully responsive.

==================================================
4. PUBLIC LANDING PAGE
   ==================================================

Create a professional landing page.

Hero section:

SEU CampusFinder

"Lost something on campus?
Let's help you find it."

Primary CTA:
[Find an Item]

Secondary CTA:
[Report Lost Item]

Another CTA:
[Report Found Item]

Hero visual:
Show a modern illustration/card-based visual representing lost and found items.

Do NOT make it look like a generic stock photo website.

Below hero:

Section:
"How SEU CampusFinder Works"

Show 4 steps:

1. Report
2. Search
3. Claim
4. Get It Back

Example:

Report
Post a lost or found item.

Search
Browse items reported by SEU students.

Claim
Submit private verification information.

Get It Back
After admin verification, connect with the other student and arrange the handover.

==================================================
5. FEATURED ITEMS SECTION
   ==================================================

Show recent public posts.

Tabs:

[All]
[Lost]
[Found]

Cards should display ONLY public information.

Each card:

--------------------------------
[Item Image]

Black Wallet

LOST

Posted by Rahim
24 Aug 2026, 3:42 PM

[View Item]
--------------------------------

Public cards must NOT show:

- Full description
- Phone number
- Email
- Private identifying information
- Claim information

==================================================
6. SEARCH PAGE
   ==================================================

Create a dedicated modern search page.

Top search bar:

"Search lost or found items..."

Filters:

Type:
[All] [Lost] [Found]

Category:
- Electronics
- Documents
- Wallet
- Keys
- Accessories
- Books
- Clothing
- Other

Date:
- Today
- This week
- This month
- Custom

Sort:
- Newest
- Oldest

Results should use responsive cards/grid.

Show useful empty states.

Example:

"No items found"

"Try a different keyword or filter."

==================================================
7. ITEM DETAILS PAGE
   ==================================================

IMPORTANT:

Public users should NOT see the complete item description.

The page should show:

Large item image

Item name

Status:
LOST / FOUND

Posted by:
Student name

Posted:
Date and time

Category

A limited public summary

Primary action:

If FOUND:
[Claim This Item]

If LOST:
[Report Found Item]

Do NOT expose:

- Phone
- Email
- Full identifying description
- Private location information
- Claim information

Add a small privacy notice:

"Some item details are hidden to protect ownership verification."

==================================================
8. AUTHENTICATION UI
   ==================================================

Create modern:

- Login
- Register
- Forgot Password
- Reset Password
- Email Verification

Registration fields:

Full Name
Student ID
SEU Email
Password
Confirm Password

Only emails ending with:

@seu.edu.bd

are allowed.

Show a clear validation message if another email is entered:

"Please use your Southeast University email address."

Login should be simple and clean.

==================================================
9. STUDENT DASHBOARD
   ==================================================

After login, show a personalized dashboard.

Example:

"Good evening, Rahim 👋"

"Track your lost and found activity."

Statistics cards:

My Lost Items
My Found Items
Pending Claims
Returned Items

Quick actions:

[Report Lost Item]
[Report Found Item]
[Browse Items]

Recent activity section.

==================================================
10. STUDENT SIDEBAR / NAVIGATION
    ==================================================

Dashboard

Browse Items

My Lost Items

My Found Items

My Claims

Notifications

Profile

Settings

Logout

On mobile, convert this into a drawer/bottom navigation depending on screen size.

==================================================
11. REPORT LOST ITEM PAGE
    ==================================================

Create a clean multi-section form.

Fields:

Item Name
Category
Image
Detailed Description
Lost Date
Lost Time
Lost Location
Identifying Details

Important UI note:

Tell the user that the detailed information is private and used for verification.

Example:

"Your detailed description will not be shown publicly. It will only be used to verify ownership if someone claims the item."

Submit:

[Report Lost Item]

After submission:

"Your lost item has been posted successfully."

The item becomes public immediately.

No admin approval is required.

==================================================
12. REPORT FOUND ITEM PAGE
    ==================================================

Similar structure:

Item Name
Category
Image
Detailed Description
Found Date
Found Time
Found Location
Identifying Details

Privacy message:

"Detailed information is kept private to help verify the rightful owner."

Submit:

[Report Found Item]

The item becomes public immediately.

==================================================
13. MY ITEMS
    ==================================================

Create two sections:

My Lost Items

My Found Items

Each item should show:

Image
Item name
Type
Created date
Current status
Actions

Possible statuses:

ACTIVE
CLAIM PENDING
CLAIM APPROVED
HANDOVER PENDING
RETURNED
CLOSED
REJECTED

Use visually distinct but subtle status badges.

==================================================
14. CLAIM UI
    ==================================================

When a student clicks:

[Claim This Item]

open a clean claim form.

Explain:

"To protect against false claims, some item details are hidden. Please provide information that proves this item belongs to you."

Fields:

Why do you believe this item is yours?

Detailed description

Identifying characteristics

Approximate lost date/time

Lost location

Additional proof/evidence

Submit:

[Submit Claim]

After submission:

"Your claim has been submitted and is waiting for administrator verification."

Status:

PENDING

==================================================
15. CLAIM STATUS PAGE
    ==================================================

Student should be able to track claims.

Example:

Claim #CLM-00124

Item:
Black Wallet

Status:
PENDING

Timeline:

✓ Claim submitted
○ Admin verification
○ Claim decision
○ Contact information
○ Handover
○ Completed

This timeline should update dynamically based on the actual claim status.

==================================================
16. CLAIM APPROVED UI
    ==================================================

When admin approves a claim, show a prominent notification.

Example:

"Claim Approved 🎉"

"Your claim for Black Wallet has been verified by the administrator."

Then show:

Found by:
Karim

Contact:
01XXXXXXXXX

Button:

[Contact Student]

Explain:

"The administrator has verified the claim. You can now contact the other student and arrange the handover yourself."

Contact information should ONLY become visible after claim approval.

==================================================
17. HANDOVER UI
    ==================================================

The website does NOT manage the physical handover.

Students arrange the handover themselves.

After the physical handover:

Finder sees:

[Mark as Handed Over]

Claimant sees:

[Confirm Item Received]

Show a confirmation dialog before changing the status.

Example:

"Have you physically handed over this item to the claimant?"

After one party confirms, show:

"Waiting for the other student's confirmation."

After both confirm:

Status:

RETURNED
CLOSED

==================================================
18. NOTIFICATION CENTER
    ==================================================

Create a modern notification dropdown/page.

Notifications can include:

- New claim submitted
- Claim approved
- Claim rejected
- Contact information available
- Handover reminder
- Item received
- Case closed
- Report updates

Use unread indicators.

==================================================
19. PROFILE PAGE
    ==================================================

Show:

Profile picture
Full name
Student ID
SEU email
Joined date

Allow editing appropriate profile information.

Do NOT allow users to change verified SEU email without a proper verification process.

==================================================
20. ADMIN DASHBOARD
    ==================================================

Admin UI should be visually distinct from the student dashboard but maintain the same branding.

Sidebar:

Dashboard

Users

Items

Claims

Reports

Analytics

Settings

Logout

Dashboard statistics:

Total Students
Active Items
Lost Items
Found Items
Pending Claims
Returned Items
Open Reports

Charts:

Lost vs Found

Items by Category

Monthly Activity

Successful Returns

Claim Approval Rate

==================================================
21. ADMIN CLAIM REVIEW
    ==================================================

This is one of the most important screens.

Admin should see:

Item information

Original lost/found information

Claimant information

Private description

Lost/found date/time

Location

Identifying characteristics

Evidence

Claim reason

The admin should be able to compare the information easily.

Actions:

[Approve Claim]

[Reject Claim]

After approval:

Contact information becomes available to the relevant students.

==================================================
22. ADMIN USER MANAGEMENT
    ==================================================

Admin can:

Search users

View user profile

View user's posts

View reports

Suspend user

Unsuspend user

Potentially deactivate account

Use confirmation dialogs for destructive actions.

==================================================
23. ADMIN REPORT MANAGEMENT
    ==================================================

Reports page:

Report ID
Reported Item
Reporter
Reason
Date
Status

Statuses:

OPEN
UNDER REVIEW
RESOLVED
DISMISSED

Actions:

Review
Remove Post
Warn User
Suspend User
Dismiss

==================================================
24. EMPTY STATES
    ==================================================

Do not leave blank screens.

Examples:

No lost items:

"No lost items yet."

"Be the first to report a lost item."

[Report Lost Item]

No claims:

"You don't have any claims yet."

No notifications:

"You're all caught up."

Use simple illustrations/icons.

==================================================
25. LOADING STATES
    ==================================================

Use skeleton loaders for:

- Item cards
- Dashboard statistics
- Tables
- Profile information

Avoid showing a blank screen while data is loading.

==================================================
26. ERROR STATES
    ==================================================

Provide friendly error messages.

Examples:

"Something went wrong."

"We couldn't load the items."

"Please try again."

For forms, show field-level validation.

Avoid technical messages such as:

NullPointerException
500 Internal Server Error

unless the user is an administrator/developer.

==================================================
27. RESPONSIVE DESIGN
    ==================================================

The UI must work properly on:

- Desktop
- Laptop
- Tablet
- Mobile

Mobile is especially important because students will primarily use phones to report/find items.

Make buttons touch-friendly.

Do not rely on hover-only interactions.

Tables should become cards or horizontally scrollable layouts on mobile.

==================================================
28. ACCESSIBILITY
    ==================================================

Follow basic accessibility principles:

- Good color contrast
- Clear labels
- Keyboard navigation
- Proper form labels
- Meaningful button text
- Alt text for images
- Avoid color-only status indicators

==================================================
29. ANIMATIONS
    ==================================================

Use subtle animations only.

Examples:

- Card hover
- Button interaction
- Page transitions
- Modal appearance
- Notification animation
- Skeleton loading

Do NOT use excessive animations.

The UI should feel fast and professional.

==================================================
30. OVERALL UX PRINCIPLE
    ==================================================

The most important user journey should always be easy to understand:

REPORT
↓
DISCOVER
↓
CLAIM
↓
VERIFY
↓
CONNECT
↓
HANDOVER
↓
CLOSE

The interface should guide the user through this journey naturally.

==================================================
31. IMPORTANT BUSINESS RULES

Always respect these rules:

1. Only @seu.edu.bd students can register.
2. Lost/found posts do NOT require admin approval.
3. Posts become public immediately.
4. Public users only see limited item information.
5. Full descriptions remain private.
6. Contact information is never publicly visible.
7. A claim requires private verification information.
8. ONLY ADMIN can approve/reject a claim.
9. Contact information is revealed only after claim approval.
10. Students arrange the physical handover themselves.
11. The website only records handover confirmation.
12. Ideally both parties confirm the handover before closing the case.
13. Students cannot modify other users' posts.
14. Students cannot access admin functions.
15. Backend authorization must enforce these restrictions.

==================================================
32. VISUAL DIRECTION

Use a modern 2026 web-app aesthetic.

Think:

Clean SaaS dashboard
+
University community platform
+
Modern marketplace-style item discovery

But do NOT make it look like an e-commerce website.

The application should feel trustworthy, minimal, polished, and easy to use.

Use consistent:
- Typography
- Spacing
- Border radius
- Shadows
- Buttons
- Icons
- Status badges
- Cards
- Forms
- Modals
- Tables

Create a reusable design system so all pages feel like part of the same application.

The final result should look like a real product that could actually be deployed for Southeast University students.