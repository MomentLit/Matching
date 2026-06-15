# Service Policy

## Confirmed Policies

- Creating a matching requires space_id, start_time, end_time, and total_price.
- start_time and end_time are parsed as OffsetDateTime first, then LocalDateTime.
- end_time must be after start_time.
- total_price must parse as an integer and must not be negative.
- New matchings start with REQUESTED status.
- Matching creation resolves the host through Space Service.
- Only active, approved spaces with a schedule covering the requested time can receive matching requests.
- Sellers cannot request their own spaces.
- Only the stored host can approve or reject a matching.
- Only the stored seller can cancel a matching.
- Only REQUESTED matchings can be approved, rejected, or canceled.
- Received matchings are queried by hostId.
- Approval locks matching rows for the space and blocks overlap with an existing APPROVED matching.

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

- HTTP error response format is not visible.
