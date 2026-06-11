# Coding Convention

## Current Service Conventions

- Main package: `com.example.matching`
- Controller structure: MatchingController handles all visible /matchings APIs.
- Service structure: MatchingService validates time and price, persists matching requests, lists sent matchings, and delegates state changes to Matching.
- Repository structure: MatchingRepository is the persistence boundary.
- DTO structure: MatchingCreateRequest is used for creation. MatchingCreateResponse, MatchingListResponse, and MatchingSearchResponse are used for responses. ApiResponse<T> wraps controller responses except 204 endpoints.
- Entity or domain structure: Matching is a JPA entity; MatchingStatus has REQUESTED, APPROVED, REJECTED, CANCELED. Role enum exists for security principal roles.
- Exception style: The service throws IllegalArgumentException and IllegalStateException directly. No @ControllerAdvice or global exception response mapper is visible.

## Naming Conventions

- Controller classes use the `*Controller` suffix when controllers exist.
- Service classes use the `*Service` suffix when services exist.
- Repository interfaces use the `*Repository` suffix and extend Spring Data repository interfaces when persistence exists.
- Request DTOs use the `*Request` suffix.
- Response DTOs use the `*Response` or `*Responses` suffix.
- Entity classes use domain nouns.
- Enum names use domain nouns such as role, status, or category.
- Method names should describe the use case or derived repository query in the existing style.
- Package names are lowercase and grouped by technical responsibility such as `controller`, `service`, `repository`, `entity`, `dto.request`, `dto.response`, and `global` where those packages exist.

## Comment Rules

- Prefer clear code and small methods over explanatory comments.
- Add comments only when they clarify non-obvious business rules or constraints.
- Avoid stale comments that restate the method name.

## Required Principles

Follow the existing naming conventions.
Do not write code in a style that conflicts with the existing codebase.
Do not create unnecessary abstractions or shared utilities.
Do not modify unrelated files.
If API behavior changes, update API_SPEC.yaml.

## Forbidden Patterns

- Do not change package structure without explicit instruction.
- Do not add dependencies without explicit instruction.
- Do not return entities directly from API responses when DTOs exist.
- Do not mix controller, service, repository, and DTO responsibilities.
- Do not introduce speculative shared utilities.
- Do not modify unrelated source, build, dependency, or configuration files.
- Do not invent error response formats that are not implemented.
