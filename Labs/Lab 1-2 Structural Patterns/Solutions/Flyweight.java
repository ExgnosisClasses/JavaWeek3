public class Runner {
    public static void main(String[] args) {
        ResourcePool r = new ResourcePool();
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
    public static final int MAX_POOL_SIZE = 10;
    public static final int DEFAULT_POOL_SIZE = 4;


    ResourcePool(int size) {
        if (size < 1) this.poolSize = ResourcePool.DEFAULT_POOL_SIZE;
        else if (size > ResourcePool.MAX_POOL_SIZE) this.poolSize = ResourcePool.MAX_POOL_SIZE;
        else {
            this.poolSize = size;
            this.available = size;

            pool = new Resource[this.poolSize];
            free = new boolean[this.poolSize];

            for (int i = 0; i < this.poolSize; i++) {
                pool[i] = new Resource();
                free[i] = true;
            }
        }
    }

    ResourcePool() {
        this(DEFAULT_POOL_SIZE);
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