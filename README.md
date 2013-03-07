# Benchmark result

## Environment
* JVM: 1.6.0_29
* JVM options: -server -ea -Xmx4G -Xms4G
* Machine: Macbook Core i7 2.8GHz, 8GB 1333Hz DDR
* OS:OS X 10.7.5

#Result:

With assertions enabled to perform a peek:

* Array2DStack: 100x(200000000 pushes) in 179045 ms
* ArrayStack: 100x(200000000 pushes) in 138021 ms
* IntBuffer2DStack: 100x(200000000 pushes) in 206271 ms
* Unsafe2DStack: 100x(200000000 pushes) in 71892 ms