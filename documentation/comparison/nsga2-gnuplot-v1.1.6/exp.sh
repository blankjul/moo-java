
 #!/bin/bash
for i in `seq 1 10`; do
	./nsga2r 0.0$i <input_data/zdt1.in
	cp all_pop.out ../pop$i.out
done





