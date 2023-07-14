select _user.user_id, _user.username, Td.training_id, Td.training_date, count(*)
from [User] _user
         inner join Training_details Td on _user.user_id = Td.user_id
group by _user.user_id, _user.username, Td.training_id, Td.training_date
having count(*) > 1
order by Td.training_date desc