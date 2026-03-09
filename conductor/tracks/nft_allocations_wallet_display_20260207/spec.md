# NFT Allocation Data & Wallet Details Screen Update

## Overview
This track addresses the need to integrate updated NFT endpoint data, which now includes allocation information, into the application. This involves persisting the new allocation data in the local database and updating the Wallet Details screen to accurately display only the NFTs associated with the currently selected wallet.

## Functional Requirements
1.  **Data Persistence:** The application shall store the new NFT allocation data received from the updated NFT endpoint.
2.  **Database Migration:** The application's database schema shall be updated to accommodate the new NFT allocation data using SQLDelight's schema evolution capabilities.
3.  **Wallet Details Display:** The Wallet Details screen shall filter and display only the NFTs that are allocated to the currently selected wallet.

## Non-Functional Requirements
1.  **Performance:** The retrieval and display of NFT data on the Wallet Details screen should remain performant, even with the addition of allocation data.
2.  **Data Integrity:** Ensure that the persisted NFT allocation data is consistent and accurate.

## Acceptance Criteria
1.  Given an updated NFT endpoint response containing allocation data, when the application syncs with the backend, then the new allocation data should be successfully stored in the local database.
2.  Given the application has new NFT allocation data, when the Wallet Details screen is viewed for a specific wallet, then only NFTs associated with that wallet's allocations should be displayed.
3.  Given a database with existing NFT data, when the application updates, then the database migration process for NFT allocation data should execute successfully without data loss.

## Out of Scope
-   Changes to the NFT endpoint on the server-side beyond what has already been implemented to include allocation data.
-   Implementation of new features related to NFT management beyond displaying allocated NFTs on the Wallet Details screen.