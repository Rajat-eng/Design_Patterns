# Design Patterns Architecture Diagrams

## 1. Observer Pattern - Event Notification System

```
┌──────────────────────────────────────────────────────────────┐
│                    GitEventPublisher                         │
│                      (Subject)                               │
├──────────────────────────────────────────────────────────────┤
│  - observers: List<IGitObserver>                            │
├──────────────────────────────────────────────────────────────┤
│  + subscribe(observer: IGitObserver)                        │
│  + unsubscribe(observer: IGitObserver)                      │
│  + notifyCommitCreated(id, msg, author)                     │
│  + notifyBranchCreated(name, from)                          │
│  + notifyMerge(source, target, user)                        │
│  + notifyStashCreated(id, msg)                              │
│  + notifyError(message)                                      │
└────────────────────┬─────────────────────────────────────────┘
                     │ maintains list
                     ↓
        ┌────────────────────────────┐
        │     IGitObserver           │
        │     (Interface)            │
        ├────────────────────────────┤
        │  + onCommitCreated()       │
        │  + onBranchCreated()       │
        │  + onMerge()               │
        │  + onStashCreated()        │
        │  + onError()               │
        └────────────┬───────────────┘
                     │ implements
         ┌───────────┴─────────┬──────────────┐
         ↓                     ↓              ↓
┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐
│LoggingObserver  │  │ AuditObserver   │  │Custom Observer│
├─────────────────┤  ├─────────────────┤  ├──────────────┤
│Logs to console  │  │Maintains history│  │Your logic    │
└─────────────────┘  └─────────────────┘  └──────────────┘

Flow:
1. repo.commit() → GitEventPublisher.notifyCommitCreated()
2. Publisher loops through all observers
3. Each observer.onCommitCreated() is called
4. Observers perform their specific actions (log, audit, notify, etc.)
```

## 2. Command Pattern - Undo/Redo System

```
┌────────────────────────────────────────────────────────────┐
│                   CommandInvoker                           │
├────────────────────────────────────────────────────────────┤
│  - history: Stack<IGitCommand>                            │
│  - undoneCommands: Stack<IGitCommand>                     │
├────────────────────────────────────────────────────────────┤
│  + executeCommand(command: IGitCommand)                   │
│  + undo()                                                  │
│  + redo()                                                  │
│  + showHistory()                                           │
└──────────────────┬─────────────────────────────────────────┘
                   │ executes & stores
                   ↓
        ┌──────────────────────────┐
        │    IGitCommand           │
        │    (Interface)           │
        ├──────────────────────────┤
        │  + execute()             │
        │  + undo()                │
        │  + getDescription()      │
        └──────────┬───────────────┘
                   │ implements
         ┌─────────┴─────────┬──────────────────┐
         ↓                   ↓                  ↓
┌──────────────────┐  ┌──────────────────┐  ┌────────────────┐
│CommitCommand     │  │CreateBranchCmd   │  │MergeCommand    │
├──────────────────┤  ├──────────────────┤  ├────────────────┤
│- repo            │  │- repo            │  │- repo          │
│- message         │  │- branchName      │  │- targetBranch  │
│- author          │  │- creator         │  │- merger        │
│- createdCommit   │  │- createdBranch   │  │- mergeCommit   │
├──────────────────┤  ├──────────────────┤  ├────────────────┤
│execute(): Create │  │execute(): Create │  │execute(): Merge│
│undo(): Delete    │  │undo(): Delete    │  │undo(): Revert  │
└──────────────────┘  └──────────────────┘  └────────────────┘

Flow:
1. invoker.executeCommand(new CommitCommand(...))
2. Command.execute() performs operation
3. Command stored in history stack
4. invoker.undo() pops from history, calls command.undo()
5. Command moved to undone stack
6. invoker.redo() pops from undone, calls command.execute()
```

## 3. Strategy Pattern - Merge Algorithms

```
┌────────────────────────────────────────────────────────┐
│              GitRepository                             │
├────────────────────────────────────────────────────────┤
│  - mergeStrategy: IMergeStrategy                      │
├────────────────────────────────────────────────────────┤
│  + merge(branchName, author)                          │
│    └──> mergeStrategy.merge(source, target)           │
└──────────────────┬───────────────────────────────────┘
                   │ uses
                   ↓
        ┌──────────────────────────────┐
        │     IMergeStrategy           │
        │     (Interface)              │
        ├──────────────────────────────┤
        │  + merge(source, target):    │
        │    Map<String, String>       │
        │  + getStrategyName(): String │
        └──────────┬───────────────────┘
                   │ implements
         ┌─────────┴──────────────┬───────────────┐
         ↓                        ↓               ↓
┌────────────────────┐  ┌────────────────────┐  ┌──────────────┐
│ThreeWayMerge       │  │FastForwardMerge    │  │Custom Strategy│
│Strategy            │  │Strategy            │  │              │
├────────────────────┤  ├────────────────────┤  ├──────────────┤
│Standard 3-way      │  │Fast-forward only   │  │Your algorithm│
│merge algorithm     │  │when possible       │  │              │
└────────────────────┘  └────────────────────┘  └──────────────┘

Usage:
IMergeStrategy strategy = new ThreeWayMergeStrategy();
GitRepository repo = new GitRepository(..., strategy);
// Can swap strategy without changing repository code
```

## 4. Composite Pattern - Validator Composition

```
┌────────────────────────────────────────────────────────┐
│            IValidator<T>                               │
│            (Interface)                                 │
├────────────────────────────────────────────────────────┤
│  + validate(data: T): ValidationResult                │
└────────────┬───────────────────────────────────────────┘
             │ implements
    ┌────────┴─────────────────────────┬──────────────────────┐
    ↓                                  ↓                      ↓
┌──────────────────┐          ┌────────────────┐    ┌────────────────┐
│Leaf Validators   │          │Composite       │    │More Validators │
├──────────────────┤          │Validator       │    ├────────────────┤
│CommitMessage     │          ├────────────────┤    │BranchName      │
│Validator         │          │- validators:   │    │Validator       │
│                  │          │  List<IVali..> │    │                │
│Checks:           │          ├────────────────┤    │Checks:         │
│- Length 5-500    │          │+ add(validator)│    │- Length 1-100  │
│- Not blank       │          │+ validate(data)│    │- Valid chars   │
└──────────────────┘          │  ├─> Loop all  │    └────────────────┘
                              │  └─> Return AND│
                              └────────────────┘
                                      ↑
                                      │ can contain
                              ┌───────┴────────┐
                              │UserValidator   │
                              ├────────────────┤
                              │Checks:         │
                              │- Name 1-100    │
                              │- Valid email   │
                              └────────────────┘

Usage:
CompositeValidator allChecks = new CompositeValidator();
allChecks.add(new CommitMessageValidator());
allChecks.add(new BranchNameValidator());
allChecks.add(new UserValidator());

ValidationResult result = allChecks.validate(data);
// Returns invalid if ANY validator fails
```

## 5. Dependency Injection - Inversion of Control

```
                High-Level Module
┌────────────────────────────────────────────────────┐
│            GitRepository                           │
│            (Depends on Abstractions)               │
├────────────────────────────────────────────────────┤
│  - commitService: ICommitService                  │
│  - branchService: IBranchService                  │
│  - stashService: IStashService                    │
│  - mergeStrategy: IMergeStrategy                  │
│  - eventPublisher: GitEventPublisher              │
├────────────────────────────────────────────────────┤
│  Constructor(ICommitService, IBranchService, ...) │
└──────────────────┬─────────────────────────────────┘
                   │ Constructor Injection
                   ↓
        ┌──────────────────────────┐
        │   Abstraction Layer      │
        │   (Interfaces)           │
        ├──────────────────────────┤
        │  ICommitService          │
        │  IBranchService          │
        │  IStashService           │
        │  IMergeStrategy          │
        └──────────┬───────────────┘
                   │ implemented by
                   ↓
        ┌──────────────────────────┐
        │   Low-Level Modules      │
        │   (Implementations)      │
        ├──────────────────────────┤
        │  CommitService           │
        │  BranchService           │
        │  StashService            │
        │  ThreeWayMergeStrategy   │
        └──────────────────────────┘

Client Code:
ICommitService commitService = new CommitService();
IBranchService branchService = new BranchService();
IStashService stashService = new StashService();
IMergeStrategy strategy = new ThreeWayMergeStrategy();

GitRepository repo = new GitRepository(
    name, creator,
    commitService,    // Injected
    branchService,    // Injected
    collaborationService,
    dagService,
    stashService,     // Injected
    deletionService,
    strategy,         // Injected
    eventPublisher
);

Benefits:
✓ Testability: Mock interfaces for testing
✓ Flexibility: Swap implementations easily
✓ Decoupling: High-level doesn't depend on low-level
```

## 6. Service Layer Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│                  Main.java, MainAdvanced.java               │
└────────────────────────────┬────────────────────────────────┘
                             │ calls
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                   Application Layer                         │
│                    GitRepository                            │
│         (Orchestrates services, enforces rules)             │
└────────────────────────────┬────────────────────────────────┘
                             │ delegates to
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                    Service Layer (SRP)                      │
├─────────────────┬─────────────────┬─────────────────────────┤
│ CommitService   │ BranchService   │ CollaborationService    │
│ - createCommit  │ - createBranch  │ - addCollaborator      │
│ - getHistory    │ - getBranch     │ - isAuthorized         │
│ - findById      │ - listBranches  │ - listCollaborators    │
├─────────────────┼─────────────────┼─────────────────────────┤
│ DAGService      │ StashService    │ CommitDeletionService  │
│ - visualizeDAG  │ - createStash   │ - deleteCommit         │
│ - findAncestor  │ - applyStash    │ - pruneUnreachable     │
│ - traverse      │ - popStash      │ - findUnreachable      │
└─────────────────┴─────────────────┴─────────────────────────┘
                             │ operates on
                             ↓
┌─────────────────────────────────────────────────────────────┐
│                    Domain Layer                             │
│          Commit, Branch, User, Repository, Stash            │
└─────────────────────────────────────────────────────────────┘

Data Flow:
1. Client → GitRepository
2. Repository validates (validators)
3. Repository delegates to service
4. Service performs logic
5. Service updates domain models
6. Repository notifies observers
7. Result returned to client
```

## 7. Complete System Integration

```
┌──────────────────────────────────────────────────────────────┐
│                         Client Code                          │
│                   (Main.java, Tests, etc.)                   │
└────────────────────────┬─────────────────────────────────────┘
                         │ creates & uses
                         ↓
        ┌────────────────────────────────────────┐
        │         GitRepository                  │
        │    (Facade + Coordinator)              │
        └──┬──┬──┬───┬───┬────┬────┬────┬────┬──┘
           │  │  │   │   │    │    │    │    │
    ┌──────┘  │  │   │   │    │    │    │    └────────┐
    │         │  │   │   │    │    │    │             │
    ↓         ↓  ↓   ↓   ↓    ↓    ↓    ↓             ↓
┌────────┐ ┌─────────────────────────────────────┐ ┌─────────┐
│Commit  │ │      Service Interfaces             │ │Observer │
│Service │ │  (ISP - Segregated Interfaces)      │ │Pattern  │
└────────┘ └─────────────────────────────────────┘ └─────────┘
    │           │                                       │
    │      ┌────┴──────┐                               │
    │      ↓           ↓                                │
    │  ┌────────┐  ┌────────┐                          │
    │  │Branch  │  │Stash   │                          │
    │  │Service │  │Service │                          │
    │  └────────┘  └────────┘                          │
    │                                                   │
    └───────────────────┬───────────────────────────────┘
                        ↓
            ┌───────────────────────┐
            │   Domain Models       │
            │ (Commit, Branch, etc.)│
            └───────────────────────┘

Pattern Layer (Cross-Cutting):
┌──────────────┬──────────────┬──────────────┬──────────────┐
│  Observer    │  Command     │  Strategy    │  Validator   │
│  (Events)    │  (Undo/Redo) │  (Merge)     │  (Check)     │
└──────────────┴──────────────┴──────────────┴──────────────┘
```

## 8. Data Flow - Complete Operation

```
Example: repo.commit("message", user)

1. Client Request
   ↓
2. GitRepository.commit()
   ├─→ Validate message (CommitMessageValidator)
   ├─→ Check authorization (CollaborationService)
   ├─→ Create commit (CommitService)
   ├─→ Update DAG (add to allCommits)
   ├─→ Update branch pointer (Branch.advanceHead)
   ├─→ Notify observers (GitEventPublisher)
   │   ├─→ LoggingObserver.onCommitCreated()
   │   └─→ AuditObserver.onCommitCreated()
   └─→ Return commit
       ↓
3. Client receives Commit object

Timeline:
┌─────────┐    ┌──────────┐    ┌─────────┐    ┌──────────┐
│Validate │ → │Authorize │ → │Execute  │ → │Notify    │
│Message  │    │User      │    │Logic    │    │Observers │
└─────────┘    └──────────┘    └─────────┘    └──────────┘
   FAIL ✗         FAIL ✗         SUCCESS ✓      SUCCESS ✓
   ↓              ↓               ↓              ↓
   Error          Error           Commit         Events
   Return         Return          Created        Published
```

## 9. Extension Points

```
To Add New Feature:

1. New Merge Strategy
   ┌─────────────────────┐
   │ IMergeStrategy      │ ← Implement this
   └─────────────────────┘
           ↓
   New class: RebaseStrategy
           ↓
   Inject into GitRepository
   ✓ No changes to existing code

2. New Observer
   ┌─────────────────────┐
   │ IGitObserver        │ ← Implement this
   └─────────────────────┘
           ↓
   New class: SlackNotifier
           ↓
   repo.subscribe(new SlackNotifier())
   ✓ No changes to existing code

3. New Command
   ┌─────────────────────┐
   │ IGitCommand         │ ← Implement this
   └─────────────────────┘
           ↓
   New class: TagCommand
           ↓
   invoker.executeCommand(new TagCommand(...))
   ✓ No changes to existing code

4. New Validator
   ┌─────────────────────┐
   │ IValidator<T>       │ ← Implement this
   └─────────────────────┘
           ↓
   New class: PathValidator
           ↓
   Add to CompositeValidator
   ✓ No changes to existing code
```

## Summary

All patterns work together:
- **Observer**: Loose coupling for notifications
- **Command**: Encapsulate operations for undo/redo
- **Strategy**: Pluggable algorithms for merge
- **Composite**: Flexible validator composition
- **DI**: Testable, flexible dependencies
- **Service Layer**: Single responsibility per service
- **Interface Segregation**: Small, focused interfaces

Result: Extensible, maintainable, testable system following SOLID principles.
