import pandas as pd
import mysql.connector
from mysql.connector import Error
import os
from dotenv import load_dotenv

load_dotenv()

db_config = {
    'user': os.getenv('USER'),
    'password': os.getenv('PASSWORD'),
    'host': os.getenv('HOST'),
    'database': os.getenv('DATABASE'),
    'port': os.getenv('PORT')
}

try:
    # Connect to the MySQL database
    conn = mysql.connector.connect(**db_config)
    
    if conn.is_connected():
        print('Connected to MySQL database')
        
        # Query to fetch data from the 'results' table grouped by 'user_id'
        query = """
            SELECT user_id,
                   AVG(extroversion) AS avg_extroversion,
                   STD(extroversion) AS std_extroversion,
                   AVG(honesty) AS avg_honesty,
                   STD(honesty) AS std_honesty,
                   AVG(neuroticism) AS avg_neuroticism,
                   STD(neuroticism) AS std_neuroticism,
                   AVG(rigidity) AS avg_rigidity,
                   STD(rigidity) AS std_rigidity
            FROM results
            GROUP BY user_id
            ORDER BY user_id;
        """
        
        # Read the data into a Pandas DataFrame
        df = pd.read_sql(query, conn)
        
        # Close the database connection
        conn.close()
        
        # Display the result
        print(df)
        
        # Save the grouped DataFrame to Excel
        df.to_excel('users/grouped_data.xlsx', index=False)
        print('Grouped data saved to Excel file.')
        
    else:
        print('Connection failed.')

except Error as e:
    print(f"Error: {e}")
finally:
    if conn.is_connected():
        conn.close()
        print('MySQL connection is closed')
