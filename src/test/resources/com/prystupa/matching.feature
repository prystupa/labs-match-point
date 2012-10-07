Feature: Match-point matching logic

  Scenario: 3-way match between two market makers and a market taker
    When market maker "A" quoted the following markets:
      | Day | AMT | Bid | Offer | AMT |
      | 1D  |     |     | 1     | 10  |
      | 2D  | 10  | 2   |       |     |
      | 3D  |     |     |       |     |
    Then RFQ service cannot quote a price for "1Dx3D"
    And no trades are generated

    When market maker "B" quoted the following markets:
      | Day | AMT | Bid | Offer | AMT |
      | 1D  |     |     |       |     |
      | 2D  |     |     | 2     | 10  |
      | 3D  | 10  | 3   |       |     |
    Then RFQ service quotes the following price for "1Dx3D":
      | AMT | Bid | Offer | AMT |
      | 10  | 2   |       |     |
    And no trades are generated

    When market taker "C" submits the following order:
      | Day | AMT | Bid | Offer | AMT |
      | 1D  | 7   | 1   |       |     |
      | 3D  |     |     | 3.5   | 10  |
    Then no trades are generated
