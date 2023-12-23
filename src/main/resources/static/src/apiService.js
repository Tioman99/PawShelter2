
export const registerUser = async (userData) => {
    try {
        const response = await fetch(`localhost:8080/users/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userData),
        });

        if (response.ok) {
            return response.json(); // You can return data if needed
        } else {
            throw new Error('Registration failed');
        }
    } catch (error) {
        throw new Error('Error:', error);
    }
};
