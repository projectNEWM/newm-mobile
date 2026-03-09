# Implementation Plan: NFT Allocation Data & Wallet Details Screen Update

## Phase 1: Database and Data Layer Integration
- [ ] Task: Update data model to include NFT allocation
    - [~] Write Failing Tests (Red Phase): Create unit tests for the updated NFT data model, ensuring it can properly store and retrieve allocation information.
    - [ ] Implement to Pass Tests (Green Phase): Modify the existing NFT data model (e.g., Kotlin data classes) to include allocation fields.
    - [ ] Refactor: Review and refactor the data model for clarity and efficiency.
- [ ] Task: Implement database schema migration
    - [ ] Write Failing Tests (Red Phase): Create integration tests that verify the database migration process for adding NFT allocation fields using SQLDelight. Ensure tests cover both initial schema creation and migration from an older schema.
    - [ ] Implement to Pass Tests (Green Phase): Modify the SQLDelight schema files to include the new allocation fields in the NFT table. Implement any necessary schema migration logic.
    - [ ] Refactor: Review and refactor the SQLDelight schema and migration logic.
- [ ] Task: Update data access layer (DAO/Repository) for NFT allocation
    - [ ] Write Failing Tests (Red Phase): Create unit tests for the DAO/Repository methods to store and retrieve NFT allocation data, and to query NFTs by wallet and allocation.
    - [ ] Implement to Pass Tests (Green Phase): Update the NFT DAO/Repository to handle saving and querying the new allocation data, including methods to filter NFTs by wallet ID.
    - [ ] Refactor: Refactor DAO/Repository code for better readability and maintainability.
- [ ] Task: Conductor - User Manual Verification 'Database and Data Layer Integration' (Protocol in workflow.md)

## Phase 2: Backend Communication and Data Synchronization
- [ ] Task: Update API client to consume new NFT endpoint response
    - [ ] Write Failing Tests (Red Phase): Create unit tests for the API client to ensure it can correctly parse the updated NFT endpoint response, including the new allocation data.
    - [ ] Implement to Pass Tests (Green Phase): Modify the API client (e.g., using Kotlin Serialization) to deserialize the new allocation fields from the NFT endpoint.
    - [ ] Refactor: Refactor API client code for better error handling and maintainability.
- [ ] Task: Implement data synchronization logic
    - [ ] Write Failing Tests (Red Phase): Create integration tests for the data synchronization logic, ensuring that NFT data, including allocations, is correctly fetched from the backend and persisted in the local database.
    - [ ] Implement to Pass Tests (Green Phase): Implement or modify the synchronization logic to fetch NFT data from the updated endpoint and store it using the updated data access layer.
    - [ ] Refactor: Optimize synchronization process for performance and reliability.
- [ ] Task: Conductor - User Manual Verification 'Backend Communication and Data Synchronization' (Protocol in workflow.md)

## Phase 3: Wallet Details UI Update
- [ ] Task: Update Wallet Details screen to filter NFTs by selected wallet
    - [ ] Write Failing Tests (Red Phase): Create Paparazzi UI tests to verify that the Wallet Details screen correctly displays only NFTs associated with the selected wallet, using the new allocation data.
    - [ ] Implement to Pass Tests (Green Phase): Modify the Wallet Details screen's ViewModel and UI components to fetch and display NFTs based on the selected wallet's allocation data.
    - [ ] Refactor: Improve UI code structure, ensure responsive design, and enhance user experience.
- [ ] Task: Conductor - User Manual Verification 'Wallet Details UI Update' (Protocol in workflow.md)