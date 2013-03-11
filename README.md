# Benchmark result

## Environment
* JVM: 1.6.0_29
* JVM options: -server -Xmx4G -Xms4G
* Machine: Macbook Core i7 2.8GHz, 8GB 1333Hz DDR
* OS:OS X 10.7.5

## Results ##

### 100,000,000 of push() then pop() ###

* ArrayLongStack: 1181 ms (push:1176 ms; pop:5ms)
* UnsafeLongStack: 910 ms (push:805 ms; pop:105ms)
* UnsafeLinkedLongStack: 577 ms (push:472 ms; pop:105ms)


