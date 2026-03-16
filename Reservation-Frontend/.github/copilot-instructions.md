
You are an expert in TypeScript, Angular, and scalable web application development. You write functional, maintainable, performant, and accessible code following Angular and TypeScript best practices.

## TypeScript Best Practices

- Use strict type checking
- Prefer type inference when the type is obvious
- Avoid the `any` type; use `unknown` when type is uncertain

## Angular Best Practices

- Always use standalone components over NgModules
- Must NOT set `standalone: true` inside Angular decorators. It's the default in Angular v20+.
- Use signals for state management
- Implement lazy loading for feature routes
- Do NOT use the `@HostBinding` and `@HostListener` decorators. Put host bindings inside the `host` object of the `@Component` or `@Directive` decorator instead
- Use `NgOptimizedImage` for all static images.
  - `NgOptimizedImage` does not work for inline base64 images.

## Accessibility Requirements

- It MUST pass all AXE checks.
- It MUST follow all WCAG AA minimums, including focus management, color contrast, and ARIA attributes.

### Components

- Keep components small and focused on a single responsibility
- Use `input()` and `output()` functions instead of decorators
- Use `computed()` for derived state
- Set `changeDetection: ChangeDetectionStrategy.OnPush` in `@Component` decorator
- Prefer inline templates for small components
- Prefer Reactive forms instead of Template-driven ones
- Do NOT use `ngClass`, use `class` bindings instead
- Do NOT use `ngStyle`, use `style` bindings instead
- When using external templates/styles, use paths relative to the component TS file.

## State Management

- Use signals for local component state
- Use `computed()` for derived state
- Keep state transformations pure and predictable
- Do NOT use `mutate` on signals, use `update` or `set` instead

## Templates

- Keep templates simple and avoid complex logic
- Use native control flow (`@if`, `@for`, `@switch`) instead of `*ngIf`, `*ngFor`, `*ngSwitch`
- Use the async pipe to handle observables
- Do not assume globals like (`new Date()`) are available.

## Services

- Design services around a single responsibility
- Use the `providedIn: 'root'` option for singleton services
- Use the `inject()` function instead of constructor injection

## Project Package Structure

- **Root structure**
  - `src/app/core`: Application-wide singletons (auth, API clients, interceptors, configuration, route guards, global error handling, logging).
  - `src/app/shared`: Reusable, feature-agnostic building blocks (presentational UI components, directives, pipes, small utilities).
  - `src/app/features`: Feature-specific packages (e.g. reservations, users, hotels), each with its own internal structure.
  - `src/app/layout`: Shell components such as main layout, navigation, header, footer, and global dialogs.
  - `src/app/config` or `src/app/environment`: Application configuration, tokens, and environment-specific wiring.

- **Core layer (`core`)**
  - Place only services, models, and utilities that are truly global.
  - Typical folders:
    - `core/services`: Global services (auth, http, configuration, notification, analytics).
    - `core/guards`: Route guards.
    - `core/interceptors`: HTTP interceptors.
    - `core/models`: Global domain models and DTOs reused across features.
    - `core/utils`: Cross-cutting helpers that are not tied to any specific feature.

- **Shared layer (`shared`)**
  - Contains reusable, stateless, and feature-agnostic pieces.
  - Typical folders:
    - `shared/components`: Reusable UI components (buttons, form controls, modals, tables).
    - `shared/directives`: Structural and attribute directives.
    - `shared/pipes`: Pipes used across multiple features.
    - `shared/forms`: Reusable form controls, configuration objects, and validators.
    - `shared/models`: UI-only models or types reused across features.

- **Features (`features`)**
  - Each feature lives under `src/app/features/<feature-name>`.
  - Feature packages must be self-contained and lazy-loadable when possible.
  - Inside each feature, use a consistent structure:
    - `features/<feature>/pages`: Route-level containers or page components.
    - `features/<feature>/components`: Smaller, feature-specific presentational components.
    - `features/<feature>/services`: Feature-specific services and data access.
    - `features/<feature>/store` or `features/<feature>/state`: Local state management (signals, facades, or other patterns).
    - `features/<feature>/models`: Feature-specific domain models and types.
    - `features/<feature>/guards`: Guards that only make sense for that feature.
    - `features/<feature>/utils`: Helpers tightly coupled to the feature.

- **Testing**
  - Keep tests close to the implementation files.
  - Use the same folder structure as production code (e.g. `*.spec.ts` next to components, services, and utilities).

- **General rules**
  - Prefer moving code from `features` to `shared` or `core` only when it is reused by at least two independent features.
  - Avoid circular dependencies between `core`, `shared`, and `features`.
  - Keep feature boundaries clear: cross-feature communication should go through well-defined services or shared abstractions, not deep imports between feature folders.
