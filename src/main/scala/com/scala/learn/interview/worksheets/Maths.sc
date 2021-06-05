// 􏰛𝑘∈[1,10] |sin(𝑘+2)|.
(1 to 10).map(k => math.abs(math.sin(k + 2))).product

// 􏰚𝑘∈[1,10]; cos𝑘>0 cos𝑘.
(1 to 10).filter(Math.cos(_) > 0).map(k => Math.sqrt(Math.cos(k))).sum

(1 to 100).reduce(_+_)
(1 to 1000).reduce(_+_)
(1 to 10000).reduce(_+_)