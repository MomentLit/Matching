# Service Policy

## Confirmed Policies

- Creating a matching requires space_id, start_time, end_time, and total_price.
- start_time and end_time are parsed as OffsetDateTime first, then LocalDateTime.
- end_time must be after start_time.
- total_price must parse as an integer and must not be negative.
- New matchings start with REQUESTED status.
- Requester cannot approve or reject their own matching.
- Only REQUESTED matchings can be approved or rejected.
- Received matchings currently return an empty list from service code.

## Validation Rules

Visible validation rules are documented from DTO annotations, entity methods, and service methods only. Any validation behavior not present in code is Needs confirmation.

## Authorization Rules

JWT stateless security is configured. /matchings/** requires authentication.

## Creation Policy

Creation behavior is documented only where visible in service or entity factory methods.

## Update Policy

Update behavior is documented only where visible in service or entity update methods.

## Deletion Policy

Deletion behavior is documented only where visible in service or entity delete methods.

## State Transition Rules

State transitions are documented only where visible in entity or service methods. Missing transitions are Needs confirmation.

## Exception Cases

The service throws IllegalArgumentException and IllegalStateException directly. No @ControllerAdvice or global exception response mapper is visible. HTTP status mapping for these exceptions is Needs confirmation unless explicitly handled in code.

## API Behavior Policy

- API behavior must be documented in `API_SPEC.yaml`.
- If API behavior changes, `API_SPEC.yaml` must be updated in the same PR.

## Needs Confirmation

- Received matching lookup currently returns an empty list; intended owner/host lookup policy is Needs confirmation.
- How a matching maps to a space host is not visible.
- Cancellation behavior is not exposed even though CANCELED exists.
- HTTP error response format is not visible.
