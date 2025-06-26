public class Runner {
    public static void main(String[] args) {
        // create the pool
        // Create it again and verify it is the same object as originally crated
        ResourcePool r = ResourcePool.getResoucePool();
        System.out.println(r);
        System.out.println(ResourcePool.getResoucePool());

        // Run the same tests as before
        System.out.println("Available: " + r.availableResources());
        Resource res = r.getResource();
        System.out.println("Resource " + res);
        System.out.println("Available: " + r.availableResources());
        r.releaseResource(res);
        System.out.println("Available: " + r.availableResources());

        for (int i = 0; i < 5; i++) {
            System.out.println("i=" + i + " Resource = " + r.getResource());
        }
    }
}

class Resource {}

class ResourcePool {
    public  Resource [] pool = null;
    private  boolean [] free = null;
    private int poolSize = 0;
    private int available = 0;
    // Assume this is set somewhere
    public static final int DEFAULT_POOL_SIZE = 4;

    // The singleton object
    private static ResourcePool thePool = null;

    // The constructor factory method
    public static ResourcePool getResoucePool() {
        if (ResourcePool.thePool == null) {
            ResourcePool.thePool = new ResourcePool();
        }
        return ResourcePool.thePool;
    }

    // Creating the pool
    private ResourcePool() {
        this.poolSize = DEFAULT_POOL_SIZE;
        this.available = DEFAULT_POOL_SIZE;

        pool = new Resource[this.poolSize];
        free = new boolean[this.poolSize];

        for (int i = 0; i < this.poolSize; i++) {
            pool[i] = new Resource();
            free[i] = true;
        }
    }



    public Resource getResource() {
        Resource r = null;
        if (this.available == 0) return r;
        for (int i = 0; i < this.poolSize; i++) {
            if (this.free[i]) {
                this.free[i] = false;
                r = this.pool[i];
                this.available--;
                break;
            }
        }
        return r;

    }

    public void releaseResource(Resource r) {
        for (int i = 0; i < this.poolSize; i++) {
            if (r == this.pool[i]) {
                this.free[i] = true;
                this.available++;
                break;
            }
        }

    }
    public int availableResources() {
        return this.available;

    }
}