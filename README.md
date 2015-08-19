# ContextSharingSimulation
Context Sharing Simulation on ONE Simulator

## Simulation setup
* [2015/08/19] Contexts in `experiment/contexts/SimulationSimple/contexts` are loaded when the simulation starts. Refer to Database object in ContextProcessor package.

## Changes

### [2015/08/19] hosts has ContextMessage

#### SimScenario

`createHosts()` can load contexts from the directory.

    protected void createHosts() {

                // smcho added
                s = new Settings(CONTEXTSUMMARY);
                String contextDirectory = s.getSetting(DIRECTORY);
                ContextMessage c = ContextMessage.load(contextDirectory, Integer.toString(j));
                host.setContextMessage(c);
                //smcho

#### DTNHost

    private ContextMessage contextMessage;

    ...
    
    public void setContextMessage(ContextMessage contextMessage) {
        this.contextMessage = contextMessage;
    }
    public ContextMessage getContextMessage() {
        return this.contextMessage;
    }

### [2015/08/10] Project structure change

All the one simulator code is in one_simulator directory, all the settings should be in the one_simulator directory

The working direcgtory becomes PATH/one_simulator

### [2015/08/10] Applications are listeners.

core/SimScenario.java/createHosts() method: for each application, add them into listener list based on what types it implements.

    protoApp = (Application)t.createIntializedObject(
            APP_PACKAGE + t.getSetting(APPTYPE_S));
    // smcho added
    // If the application implements connectionListner, it should be added to the lister group
    if (protoApp instanceof ConnectionListener) {
        addConnectionListener((ConnectionListener) protoApp);
    }
    if (protoApp instanceof MessageListener) {
        addMessageListener((MessageListener) protoApp);
    }
    if (protoApp instanceof MovementListener) {
        addMovementListener((MovementListener) protoApp);
    }
    if (protoApp instanceof UpdateListener) {
        addUpdateListener((UpdateListener) protoApp);
    }
    if (protoApp instanceof ApplicationListener) {
        addApplicationListener((ApplicationListener) protoApp);
    }
    // smcho added end
