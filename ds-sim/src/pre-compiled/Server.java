public class Server {
    private int serverID;
    private int state;
    private float availableTime;
    private int numCPUs;
    private int memory;
    private int disk;
    private int jobsRunning;
    private int jobsTotal;

    public Server(int serverID, int state, float availableTime, int numCPUs, int memory, int disk, int jobsRunning, int jobsTotal) {
        this.serverID = serverID;
        this.state = state;
        this.availableTime = availableTime;
        this.numCPUs = numCPUs;
        this.memory = memory;
        this.disk = disk;
        this.jobsRunning = jobsRunning;
        this.jobsTotal = jobsTotal;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getAvailableTime() {
        return availableTime;
    }

    public void setAvailableTime(float availableTime) {
        this.availableTime = availableTime;
    }

    public int getNumCPUs() {
        return numCPUs;
    }

    public void setNumCPUs(int numCPUs) {
        this.numCPUs = numCPUs;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }

    public int getJobsRunning() {
        return jobsRunning;
    }

    public void setJobsRunning(int jobsRunning) {
        this.jobsRunning = jobsRunning;
    }

    public int getJobsTotal() {
        return jobsTotal;
    }

    public void setJobsTotal(int jobsTotal) {
        this.jobsTotal = jobsTotal;
    }
}

