

COPY outgoing.channel_pool (sender,receiver,auditor,min_active,max_active,deposit,close_timeout,settle_timeout,audit_timeout,close_blocks_count,shutdown)
TO 'C:\Users\User\Desktop\outgoing.channel_pool.csv'
WITH HEADER = TRUE;

COPY outgoing.channel (address,status,transferred,current_nonce,synced_nonce)
TO 'C:\Users\User\Desktop\outgoing.channel.csv'
WITH HEADER = TRUE;

COPY channel.transfer (channel_id,transfer_id,value,locked,signature)
TO 'C:\Users\User\Desktop\channel.transfer.csv'
WITH HEADER = TRUE;

COPY channel.unlock (channel_id,transfer_id,signature)
TO 'C:\Users\User\Desktop\channel.unlock.csv'
WITH HEADER = TRUE;

COPY incoming.channel (address,status,transferred,current_nonce,synced_nonce)
TO 'C:\Users\User\Desktop\incoming.channel.csv'
WITH HEADER = TRUE;


cat 'C:\Users\User\Desktop\outgoing.channel_pool.csv' | gawk -F',' '{print " SET \""$1"\" \""$2"\" \n"}' | redis-cli --pipe 
cat 'C:\Users\User\Desktop\outgoing.channel.csv' | gawk -F',' '{print " SET \""$1"\" \""$2"\" \n"}' | redis-cli --pipe 
cat 'C:\Users\User\Desktop\channel.transfer.csv' | gawk -F',' '{print " SET \""$1"\" \""$2"\" \n"}' | redis-cli --pipe 
cat 'C:\Users\User\Desktop\channel.unlock.csv' | gawk -F',' '{print " SET \""$1"\" \""$2"\" \n"}' | redis-cli --pipe 
cat 'C:\Users\User\Desktop\incoming.channel.csv' | gawk -F',' '{print " SET \""$1"\" \""$2"\" \n"}' | redis-cli --pipe 

---
cat data.txt | redis-cli --pipe
cat 'C:\Users\User\Desktop\outgoing.channel_pool.csv' | redis-cli --pipe 
cat 'C:\Users\User\Desktop\outgoing.channel.csv' | redis-cli --pipe 
cat 'C:\Users\User\Desktop\channel.transfer.csv' | redis-cli --pipe 
cat 'C:\Users\User\Desktop\channel.unlock.csv' | redis-cli --pipe 
cat 'C:\Users\User\Desktop\incoming.channel.csv' | redis-cli --pipe 
---

//cat 'C:\Users\User\Desktop\outgoing.channel_pool.csv' | gawk -F',' '{print " SET \""$1"\" \""$2"\" \""$3"\" \""$4"\" \""$5"\" \""$6"\" \""$7"\" \""$8"\" \""$9"\" \""$10"\" \""$11"\" \n"}' | redis-cli --pipe 
