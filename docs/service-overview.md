# Service Overview

## Service Name

Matching

## Service Responsibility

Matching creation, sent/received lookup, host approval/rejection, and seller cancellation.

## Technology Stack

- Language: Java 21
- Framework: Spring Boot 4.0.5
- Build Tool: Gradle
- Database: PostgreSQL via Spring Data JPA; H2 test runtime dependency
- Other: Lombok is used where visible. JWT and Spring Security are used where security classes are visible.

## Main Package Structure

- Main package: `com.example.matching`
- Controller: MatchingController handles all visible /matchings APIs.
- Service: MatchingService validates time and price, persists matching requests, lists sent matchings, and delegates state changes to Matching.
- Repository: MatchingRepository is the persistence boundary.
- DTO: MatchingCreateRequest is used for creation. MatchingCreateResponse, MatchingListResponse, and MatchingSearchResponse are used for responses. ApiResponse<T> wraps controller responses except 204 endpoints.
- Entity/domain: Matching is a JPA entity; MatchingStatus has REQUESTED, APPROVED, REJECTED, CANCELED. Role enum exists for security principal roles.

## Main Domains

Matching entity with MatchingStatus enum. Matching requests store spaceId, sellerId, and hostId by value.

## Main Features

Matching creation, sent/received lookup, host approval/rejection, seller cancellation, and overlap prevention.

## Main APIs

Visible APIs under /matchings. Full details are in API_SPEC.yaml.

## Data Access Structure

MatchingRepository extends JpaRepository<Matching, Long> and supports seller/host lookup plus space-level pessimistic locking for approval.

## Exception Handling

The service throws IllegalArgumentException and IllegalStateException directly. No @ControllerAdvice or global exception response mapper is visible.

## Test Structure

Matching entity, service policy, JWT parsing, and application context tests are present under com.example.matching.

## API Documentation

This service uses `API_SPEC.yaml` as the main API specification.
When API behavior changes, `API_SPEC.yaml` must be updated in the same PR.

## Development Notes

- Preserve the current single-module service structure.
- Follow the existing package and naming conventions.
- Keep controller, service, repository, entity, and DTO responsibilities separate where those layers exist.
- Do not add cross-service behavior unless it is visible in code or explicitly specified by an Issue.
- If implementation changes API behavior, update `API_SPEC.yaml` in the same PR.

## Needs Confirmation

- HTTP error response format is not visible.
