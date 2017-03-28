InfluxDB influxDB = InfluxDBFactory.connect("http://172.17.0.2:8086", "root", "root");

手动批量插入
BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy("autogen")
                .consistency(ConsistencyLevel.ALL)
                .build();
Point point1 = Point.measurement("cpu")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("idle", 90L)
                    .addField("user", 9L)
                    .addField("system", 1L)
                    .build();
Point point2 = Point.measurement("disk")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("used", 80L)
                    .addField("free", 1L)
                    .build();
batchPoints.point(point1);
batchPoints.point(point2);
influxDB.write(batchPoints);


自动批量插入
influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);
influxDB.write(dbName, "autogen", point1);
influxDB.write(dbName, "autogen", point2);

influxDB.close();

其他特性
gzip
influxDB.enableGzip()


