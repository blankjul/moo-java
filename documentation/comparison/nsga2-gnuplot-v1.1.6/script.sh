
 #!/bin/bash
for i in `seq 1 30`; do
	./nsga2r 0.$i <input_data/zdt3.in
	cp best_pop.out ../zdt3_$i.out
done





